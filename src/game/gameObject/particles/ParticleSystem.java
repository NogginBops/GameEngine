package game.gameObject.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicMovable;
import game.image.effects.ColorTintFilter;
import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class ParticleSystem extends BasicMovable implements Paintable {
	
	//JAVADOC: ParticleSystem
	
	//TODO: Figure out what should and should not be relative coordinates. (Most things probably)
	
	//TODO: ParticleColliders
	
	//TODO: Other particle related things like color/size over lifetime and other effects
	
	//TODO: Rotation?
	
	//NOTE: When thread scheduling tasks are implemented, should this be it's own task?
	
	//TODO: Particle coordinates should represent the center of the particle.
	//Only the center should be collision checked for speed.
	//Most of the times this wont even be noticed.
	
	private Particle[] particles;
	
	private ArrayList<ParticleEmitter> emitters;
	
	private ArrayList<ParticleEffector> effectors;
	
	//NOTE: Does this need optimization?
	//FIXME: This solution is generating a lot of entries. Find a way to cut number entries!
	//The images can not be generated at draw-time for performance, a few solutions could be;
	//  - Approximation of the color key in the second hashmap. (This solution will probably cut the allocation the most)
	//  - A way to draw all the particles with the same image at the same time (batching)
	private HashMap<Integer, HashMap<Color, BufferedImage>> imageMap;

	//TODO: Remove?
	/**
	 * 
	 */
	public boolean debug = false;
	
	/**
	 * @param rect
	 * @param zOrder
	 * @param maxParticles 
	 */
	public ParticleSystem(Rectangle rect, int zOrder, int maxParticles) {
		super(rect, zOrder);
		particles = new Particle[maxParticles];
		for (int i = 0; i < particles.length; i++) {
			//TODO: A way to change the way particles are being initialized.
			particles[i] = new Particle(0, 0, 10, 10, 1, Color.WHITE, 0);
			particles[i].active = false;
		}
		emitters = new ArrayList<>();
		effectors = new ArrayList<>();
		imageMap = new HashMap<>();
	}

	@Override
	public void paint(Graphics2D g2d) {
		//TODO: Remove? Make a better system for debugging bounding areas?
		if(debug){
			g2d.setColor(Color.magenta);
			g2d.draw(bounds);
			g2d.setColor(Color.cyan);
			for (ParticleEmitter particleEmitter : emitters) {
				switch (particleEmitter.shape) {
				case RECTANGLE:
					g2d.draw(new Rectangle2D.Float(x + particleEmitter.x, y + particleEmitter.y, particleEmitter.width, particleEmitter.height));
					break;
				case CIRCLE:
					g2d.draw(new Ellipse2D.Float(x + particleEmitter.x, y + particleEmitter.y, particleEmitter.radius * 2, particleEmitter.radius * 2));
					break;
				default:
					break;
				}
			}
		}
		
		
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				//Paint
				
				//FIXME: This is generating a lot of memory
				BufferedImage image = getParticleImage(particles[i]);
				
				if(image == null){
					g2d.setColor(particles[i].color);
					g2d.drawRect((int)(x + (particles[i].x - (particles[i].width * particles[i].scaleX))),
							(int)(y + (particles[i].y) - ((particles[i].height * particles[i].scaleY)/2)),
							(int)(particles[i].width * particles[i].scaleX),
							(int)(particles[i].height * particles[i].scaleY));
				}else{
					g2d.drawImage(image,
							(int)(x + (particles[i].x - ((particles[i].width * particles[i].scaleX)/2))),
									(int)(y + (particles[i].y - ((particles[i].height * particles[i].scaleY)/2))),
									(int)(particles[i].width * particles[i].scaleX),
									(int)(particles[i].height * particles[i].scaleY), null);
				}
			}
		}
	}
	
	private BufferedImage getParticleImage(Particle particle) {
		if(hasImage(particle) == false){
			if(generateParticleImage(particle) == false){
				return null;
			}
		}
		
		return imageMap.get(particle.image).get(particle.color);
	}
	
	/**
	 * 
	 * @param particle
	 * @return
	 */
	private boolean hasImage(Particle particle){
		if(imageMap.containsKey(particle.image)){
			if(imageMap.get(particle.image).containsKey(particle.color)){
				return true;
			}
		}
		return false;
	}
	
	private boolean generateParticleImage(Particle particle){
		if(imageMap.containsKey(particle.image)){
			imageMap.get(particle.image).put(particle.color,
					new ColorTintFilter(particle.color, 1).filter(imageMap.get(particle.image).get(Color.white), null));
			return true;
		}
		return false;
	}

	@Override
	public BufferedImage getImage() {
		//NOTE: A particle system is a very dynamic system so rendering to a image is probably going to be slower than using the paint method.
		//But there should be a option to render to a image so that image effects can be applied.
		return null;
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
		
		for (ParticleEffector particleEffector : effectors) {
			if(particleEffector.enabled == true){
				particleEffector.effect(particles, deltaTime);
			}
		}
		
		//Update particle movement
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				particles[i].currLifetime -= (deltaTime);
				if(particles[i].currLifetime <= 0){
					particles[i].active = false;
				}
				
				particles[i].x = particles[i].x + (particles[i].dx * deltaTime);
				particles[i].y = particles[i].y + (particles[i].dy * deltaTime);
				
				//Scale with lifetime //TODO: Move to a ParticleEffector
				
				//particles[i].scaleX = (float) MathUtils.max(0.2f, ((particles[i].currLifetime / particles[i].lifetime)));
				//particles[i].scaleY = (float) MathUtils.max(0.2f, ((particles[i].currLifetime / particles[i].lifetime)));
								
				if(hasImage(particles[i]) == false){
					generateParticleImage(particles[i]);
				}
				
				if(MathUtils.isOutside(x + particles[i].x, x, x + (width - (particles[i].width * particles[i].scaleX)))!= 0 ||
						MathUtils.isOutside(y + particles[i].y, y, y + (height - (particles[i].height * particles[i].scaleY)))!= 0){
					
					//TODO: Different edge modes
					
					particles[i].active = false;
					
					/*particles[i].x = MathUtils.clamp(particles[i].x, x, x + (width - particles[i].width));
					particles[i].y = MathUtils.clamp(particles[i].y, y, y + (height - particles[i].height));
					
					particles[i].dx = -particles[i].dx;
					particles[i].dy = -particles[i].dy;*/
				}
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
				
				particles[i].currLifetime = particles[i].lifetime;
				
				//Activate and return
				particles[i].active = true;
				return particles[i];
			}
		}
		//Was not able to spawn a particle
		return null;
	}
	
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
	
	//NOTE: Should there be a better way of keeping track of the different particle images 
	/**
	 * @param imageID
	 * @param image
	 */
	public void addImage(int imageID, BufferedImage image){
		HashMap<Color, BufferedImage> tempMap = new HashMap<>();
		tempMap.put(Color.WHITE, image);
		imageMap.put(imageID, tempMap);
	}
	
	//Particle collides?
}
