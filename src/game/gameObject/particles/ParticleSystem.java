package game.gameObject.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import game.Game;
import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicRotatable;
import game.gameObject.transform.BoxTransform;
import game.gameObject.transform.Transform;
import game.image.effects.ColorTintFilter;
import game.util.image.ImageUtils;
import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class ParticleSystem extends BasicRotatable implements Paintable {
	
	//JAVADOC: ParticleSystem
	
	//TODO: Figure out what should and should not be relative coordinates. (Most things probably)
	
	//TODO: ParticleColliders?
	//Should these be a separate thing or should they just be implemented through a particleEffector
	//Maybe the physics system could do this.
		
	//TODO: Rotation on particles?
	
	/**
	 * 
	 */
	public static final ParticleCustomizer DESTROY = (particle) -> { particle.active = false; };
	
	/**
	 * 
	 */
	public static final ParticleCustomizer DO_NOTHING = (particle) -> { };

	//NOTE: When thread scheduling tasks are implemented, should this be it's own task?
	
	private Particle[] particles;
	
	private ArrayList<ParticleEmitter> emitters;
	
	private ArrayList<ParticleEffector> effectors;
	
	private ArrayList<ParticleEffector> activeEffectors;
	
	private int activeParticles = 0;
	
	//NOTE: Does this need further optimization?
	private HashMap<Integer, HashMap<Color, BufferedImage>> imageMap;
	
	private BoxTransform<GameObject> boxTransform;
	
	//NOTE: Should these be public?
	
	/**
	 * 
	 */
	public int rGranularity = 5;
	
	/**
	 * 
	 */
	public int gGranularity = 5;
	
	/**
	 * 
	 */
	public int bGranularity = 5;
	
	/**
	 * 
	 */
	public int aGranularity = 5;
	
	/**
	 * 
	 */
	public ParticleCustomizer edgeAction = DESTROY;

	//TODO: Remove?
	/**
	 * 
	 */
	public boolean debug = false;
	
	//NOTE: Should a particle system be any other shape than a rectangle?
	/**
	 * @param transform 
	 * @param zOrder 
	 * @param maxParticles 
	 * @param customizer 
	 */
	public ParticleSystem(BoxTransform<GameObject> transform, int zOrder, int maxParticles, ParticleCustomizer customizer) {
		super(transform, new Rectangle2D.Float(0, 0, transform.getWidth(), transform.getHeight()), zOrder, 0);
		
		setTransform(transform);
		boxTransform = transform;
		
		particles = new Particle[maxParticles];
		for (int i = 0; i < particles.length; i++) {
			//NOTE: How should the standard particle look like?
			particles[i] = new Particle(0, 0, 10, 10, 1, Color.WHITE, 0);
			particles[i].active = false;
			
			//A initial customization of the particles
			if(customizer != null){
				customizer.customize(particles[i]);
				
				if(particles[i].active){
					activeParticles++;
				}
			}
		}
		emitters = new ArrayList<>();
		effectors = new ArrayList<>();
		imageMap = new HashMap<>();
	}

	//Temp var
	BufferedImage image;
	
	@Override
	public void paint(Graphics2D g2d) {
		AffineTransform at = g2d.getTransform();
		g2d.transform(transform.getAffineTransform());
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				//Paint
				
				//FIXME: This is generating a lot of memory
				//Is this fixed with the granularities?
				 image = getParticleImage(particles[i]);
				
				if(image == null){
					g2d.setColor(particles[i].color);
					g2d.drawRect((int)((particles[i].x - (particles[i].width * particles[i].scaleX/2))),
							(int)((particles[i].y) - ((particles[i].height * particles[i].scaleY)/2)),
							(int)(particles[i].width * particles[i].scaleX),
							(int)(particles[i].height * particles[i].scaleY));
				}else{
					g2d.drawImage(image,
							(int)((particles[i].x - ((particles[i].width * particles[i].scaleX)/2))),
									(int)((particles[i].y - ((particles[i].height * particles[i].scaleY)/2))),
									(int)(particles[i].width * particles[i].scaleX),
									(int)(particles[i].height * particles[i].scaleY), null);
				}
			}
		}
		
		//TODO: Remove? Make a better system for debugging bounding areas?
		if(debug){
			g2d.setColor(Color.cyan);
			for (ParticleEmitter particleEmitter : emitters) {
				switch (particleEmitter.shape) {
				case RECTANGLE:
					g2d.draw(new Rectangle2D.Float(particleEmitter.x, particleEmitter.y, particleEmitter.width, particleEmitter.height));
					break;
				case CIRCLE:
					g2d.draw(new Ellipse2D.Float(particleEmitter.x - particleEmitter.radius, particleEmitter.y - particleEmitter.radius, particleEmitter.radius * 2, particleEmitter.radius * 2));
					break;
				default:
					break;
				}
			}
			
			for (int i = 0; i < particles.length; i++) {
				if(particles[i].active == true){
					g2d.setColor(Color.yellow);
					g2d.drawRect((int)((particles[i].x - (particles[i].width * particles[i].scaleX/2))),
							(int)((particles[i].y) - ((particles[i].height * particles[i].scaleY)/2)),
							(int)(particles[i].width * particles[i].scaleX),
							(int)(particles[i].height * particles[i].scaleY));
					g2d.setColor(Color.GREEN);
					g2d.drawRect((int)(particles[i].x) - 1,
							(int)(particles[i].y) - 1,
							2, 2);
				}
			}
			
			g2d.setColor(Color.PINK);
			g2d.fillRect((int)(0), (int)(0), 2, 2);
			
			g2d.setColor(Color.magenta);
			g2d.draw(getShape());
		}
		g2d.setTransform(at);
	}
	
	//TODO: roundColor is being called 3 times in succession, there is a better solution
	//Should the methods be merged
	
	private BufferedImage getParticleImage(Particle particle) {
		Color rColor = roundColor(particle.color);
		if(hasImage(particle, rColor) == false){
			if(generateParticleImage(particle, rColor) == false){
				return null;
			}
		}
		
		return imageMap.get(particle.image).get(rColor);
	}
	
	/**
	 * 
	 * @param particle
	 * @return
	 */
	private boolean hasImage(Particle particle, Color color){
		if(imageMap.containsKey(particle.image)){
			if(imageMap.get(particle.image).containsKey(roundColor(color))){
				return true;
			}
		}
		return false;
	}
	
	private boolean generateParticleImage(Particle particle, Color color){
		if(imageMap.containsKey(particle.image)){
			imageMap.get(particle.image).put(color,
					new ColorTintFilter(color, 1).filter(imageMap.get(particle.image).get(Color.white), null));
			return true;
		}
		return false;
	}
	
	/**
	 * Round the input color to a specified granularity. 
	 * Used to round the particles color, to reduce image generation and caching while keeping the particle data unchanged.
	 * @param color
	 * @return
	 */
	private Color roundColor(Color color){
		if(rGranularity == 1 && gGranularity == 1 &&
				bGranularity == 1 && aGranularity == 1){
			return color;
		}
		
		//TODO: Find a more efficient way to do this
		
		int r = color.getRed();
		r = roundColorValue(r, rGranularity);
		
		int g = color.getGreen();
		g = roundColorValue(g, gGranularity);
		
		int b = color.getBlue();
		b = roundColorValue(b, bGranularity);
		
		int a = color.getAlpha();
		a = roundColorValue(a, aGranularity);
		
		return new Color(r, g, b, a);
	}
	
	private int roundColorValue(int value, int granularity){
		if(value % granularity != 0){
			if(value % granularity <= granularity/2){
				value -= value % granularity;
			}else{
				value += granularity - (value % granularity);
			}
			
			value = MathUtils.clamp(value, 0, 255);
		}
		return value;
	}

	@Override
	public BufferedImage getImage() {
		//NOTE: A particle system is a very dynamic system so rendering to a image is probably going to be slower than using the paint method.
		//But there should be a option to render to a image so that image effects can be applied.
		return null;
	}
	
	/**
	 * @return
	 */
	@Override
	public void setTransform(Transform<GameObject> transform) {
		if(transform instanceof BoxTransform){
			super.setTransform(transform);
			boxTransform = (BoxTransform<GameObject>) transform;
		}else{
			//NOTE: Is this needed?
			Game.log.logError("Trying to assign a non BoxTransform to a particle system! This is not suported!", "ParticleSystem", "Transform", "Particle");
		}
	}
	
	/**
	 * @return
	 */
	public BoxTransform<GameObject> getBoxTransform(){
		return boxTransform;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		//Update emitters
		for (ParticleEmitter particleEmitter : emitters) {
			if(particleEmitter.enabled == true){
				particleEmitter.Update(this, deltaTime);
			}
		}
		
		//Get active effectors
		activeEffectors = new ArrayList<>();
		for (ParticleEffector particleEffector : effectors) {
			if(particleEffector.enabled == true){
				activeEffectors.add(particleEffector);
			}
		}
		
		//Update particle movement
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				particles[i].currLifetime -= (deltaTime);
				if(particles[i].currLifetime <= 0){
					particles[i].active = false;
					activeParticles--;
					continue;
				}
				
				//Apply effectors to all particle
				for (ParticleEffector particleEffector : activeEffectors) {
					particleEffector.effect(particles[i], deltaTime);
				}
				
				particles[i].dx += particles[i].accX * deltaTime;
				particles[i].dy += particles[i].accY * deltaTime;
				
				particles[i].x += (particles[i].dx * deltaTime);
				particles[i].y += (particles[i].dy * deltaTime);
				
				//NOTE: Should this be done here?
				Color rColor = roundColor(particles[i].color);
				if(hasImage(particles[i], rColor) == false){
					generateParticleImage(particles[i], rColor);
				}
				
				//NOTE: Should these be temporary variables or not
				int isOutsideX = MathUtils.isOutside(particles[i].x, 0, boxTransform.getWidth());
				int isOutsideY = MathUtils.isOutside(particles[i].y, 0, boxTransform.getHeight());
				
				//NOTE: Should both axis be stopped when colliding a edge or should just one be stopped?
				//If that behavoiur is desired should that be implemented using the edgeAction;
				if(isOutsideX != 0){
					particles[i].dx = 0;
					particles[i].x = isOutsideX < 0 ? 0 : getWidth();
				}
				
				if(isOutsideY != 0){
					particles[i].dy = 0;
					particles[i].y = isOutsideY < 0 ? 0 : getHeight();
				}
				
				if(isOutsideX != 0 || isOutsideY != 0){
					edgeAction.customize(particles[i]);
					
					if(particles[i].active == false){
						activeParticles--;
						continue;
					}
				}
				
				/*
				//NOTE: Does this consider particle width? Should this be done?
				if(MathUtils.isOutside(x + particles[i].x, x, x + (width - (particles[i].width * particles[i].scaleX))) != 0 ||
						MathUtils.isOutside(y + particles[i].y, y, y + (height - (particles[i].height * particles[i].scaleY))) != 0){
					
					//TODO: Different edge modes
					
					edgeMode.customize(particles[i]);
					
					//particles[i].active = false;
					
					particles[i].x = MathUtils.clamp(particles[i].x, x, x + (width - particles[i].width));
					particles[i].y = MathUtils.clamp(particles[i].y, y, y + (height - particles[i].height));
					
					particles[i].dx = -particles[i].dx;
					particles[i].dy = -particles[i].dy;
				}
			*/
			}
		}
	}

	
	//NOTE: Should this be private to make this only accessible in the particleEmitter class
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public Particle spawnParticle(float x, float y){
		//Find inactive particle
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == false){
				//Set position
				particles[i].setPosition(x, y);
				particles[i].setVelocity(0, 0);
				particles[i].setAcceleration(0, 0);
				
				//Reset lifetime
				particles[i].currLifetime = particles[i].lifetime;
				
				activeParticles++;
				
				//Activate and return
				particles[i].active = true;
				return particles[i];
			}
		}
		//Was not able to spawn a particle
		return null;
	}
	
	//TODO: Get and set particles mess up the particle metrics and they do not really add any useful functionality.
	//Consider removing them.
	
	/**
	 * @return
	 */
	public Particle[] getParticles(){
		return particles;
	}
	
	/**
	 * @param particles
	 */
	public void setParticles(Particle[] particles){
		this.particles = particles;
	}
	
	/**
	 * @param customizer
	 */
	public void customizeParticles(ParticleCustomizer customizer){
		boolean wasActive;
		for (int i = 0; i < particles.length; i++) {
			wasActive = particles[i].active;
			
			if(customizer != null){
				customizer.customize(particles[i]);
				
				if(particles[i].active != wasActive){
					if(wasActive == true){
						activeParticles--;
					}else{
						activeParticles++;
					}
				}
			}
		}
	}
	
	/**
	 * @param emitter
	 */
	public void addEmitter(ParticleEmitter emitter){
		emitters.add(emitter);
	}
	
	/**
	 * @param effector
	 */
	public void addEffector(ParticleEffector effector){
		effectors.add(effector);
	}
	
	//NOTE: Should there be a better way of keeping track of the different particle images?
	/**
	 * @param imageID
	 * @param image
	 */
	public void addImage(int imageID, BufferedImage image){
		image = ImageUtils.toSystemOptimizedImage(image);
		
		HashMap<Color, BufferedImage> tempMap = new HashMap<>();
		tempMap.put(Color.WHITE, image);
		imageMap.put(imageID, tempMap);
	}
	
	/**
	 * @param granularity
	 */
	public void setAllGranularities(int granularity){
		granularity = MathUtils.clamp(granularity, 1, 255);
		
		rGranularity = granularity;
		gGranularity = granularity;
		bGranularity = granularity;
		aGranularity = granularity;
	}
	
	@Override
	public String[] getDebugValues() {
		String[] prevDebug = super.getDebugValues();
		
		String[] newDebug = new String[]{ 
				"<b>Particles: </b>" + particles.length,
				"<b>Active particles: </b>" + activeParticles
		};
		
		String[] merge = new String[prevDebug.length + newDebug.length];
		System.arraycopy(prevDebug, 0, merge, 0, prevDebug.length);
		System.arraycopy(newDebug, 0, merge, prevDebug.length, newDebug.length);
		
		return merge;
	}
}
