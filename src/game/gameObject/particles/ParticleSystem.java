package game.gameObject.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import game.Game;
import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicMovable;
import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class ParticleSystem extends BasicMovable implements Paintable {
	
	//JAVADOC: ParticleSystem
	
	//TODO: ParticleSystem
	
	//TODO: Figure out what should and should not be relative coordinates.
	
	//TODO: Particle forces
	
	//TODO: ParticleColliders
	
	//TODO: Other particle related things like color/size over lifetime and other effects
	
	//TODO: Rotation?
	
	private Particle[] particles;
	
	private ArrayList<ParticleEmitter> emitters;
	
	private HashMap<Integer, HashMap<Color, BufferedImage>> imageMap;

	//TODO: Remove
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
			particles[i] = new Particle(0, 0, 10, 10, 1, Color.WHITE, 0);
			particles[i].active = false;
		}
		emitters = new ArrayList<>();
		imageMap = new HashMap<>();
	}

	@Override
	public void paint(Graphics2D g2d) {
		//TODO: Remove
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
				BufferedImage image = getParticleImage(particles[i]);
				
				if(image == null){
					g2d.setColor(Color.magenta);
					g2d.drawRect((int)(x + (particles[i].x - (particles[i].width * particles[i].scaleX))),
							(int)(y + (particles[i].y)),
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
		if(imageMap.containsKey(particle.image)){
			if(imageMap.get(particle.image) == null){
				imageMap.put(particle.image, new HashMap<Color, BufferedImage>());
			}
			if(imageMap.get(particle.image).containsKey(particle.color)){
				return imageMap.get(particle.image).get(particle.color);
			}else{
				//Generate
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
	
	@Override
	public void update(long timeNano) {
		super.update(timeNano);
		
		//Update emitters
		for (ParticleEmitter particleEmitter : emitters) {
			particleEmitter.Update(this, timeNano / 1000000000f);
		}
		
		//Update particle movement
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				particles[i].currLifetime -= (timeNano / 1000000000f);
				if(particles[i].currLifetime <= 0){
					particles[i].active = false;
				}
				
				particles[i].x = particles[i].x + (particles[i].dx * (timeNano / 1000000000f));
				particles[i].y = particles[i].y + (particles[i].dy * (timeNano / 1000000000f));
				
				//Scale with lifetime
				particles[i].scaleX = (float)((particles[i].currLifetime / particles[i].lifetime));
				particles[i].scaleY = (float)((particles[i].currLifetime / particles[i].lifetime));
			
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
	 * @param imageID
	 * @param image
	 */
	public void addImage(int imageID, BufferedImage image){
		HashMap<Color, BufferedImage> tempMap = new HashMap<>();
		tempMap.put(Color.WHITE, image);
		imageMap.put(imageID, tempMap);
	}
	
	
	/**
	 * 
	 * @author Julius Häger
	 *
	 */
	public enum EmissionShape{
		/**
		 * 
		 */
		CIRCLE,
		/**
		 * 
		 */
		RECTANGLE;
	}
	
	/**
	 * @author Julius Häger
	 *
	 */
	public class ParticleEmitter{
		
		//TODO: Figure out what members that should be exposed
		
		/**
		 * 
		 */
		public float emissionRate;
		
		/**
		 * 
		 */
		public EmissionShape shape;
		
		/**
		 * 
		 */
		public float x;
		
		/**
		 * 
		 */
		public float y;
		
		/**
		 * 
		 */
		public float width;
		
		/**
		 * 
		 */
		public float height;
		
		/**
		 * 
		 */
		public float radius;
		
		private Random rand = new Random();
		
		private float timer;
		
		/**
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 * @param emissionRate
		 */
		public ParticleEmitter(float x, float y, float width, float height, float emissionRate) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.emissionRate = emissionRate;
			timer = 1/emissionRate;
			
			shape = EmissionShape.RECTANGLE;
		}
		
		/**
		 * @param x
		 * @param y
		 * @param radius
		 * @param emissionRate
		 */
		public ParticleEmitter(float x, float y, float radius, float emissionRate) {
			this.x = x;
			this.y = y;
			this.radius = radius;
			this.emissionRate = emissionRate;
			timer = 1/emissionRate;
			
			shape = EmissionShape.CIRCLE;
		}
		
		/**
		 * @param system
		 * @param deltaTime
		 */
		public void Update(ParticleSystem system, float deltaTime){
			timer -= deltaTime;
			while(timer <= 0){
				timer += 1/emissionRate;
				
				//Spawn particle
				Point2D.Float point = getRandomPoint();
				
				//Relative coordinates?
				Particle p = system.spawnParticle(point.x, point.y);
				
				//NOTE: Should something be done to the particles?
				//Lifetime things and such...
				
				//Cannot spawn any more particles
				if(p == null){
					break;
				}
				
				//TODO: Emission variables?
				//p.dx = rand.nextFloat() * 100;
				p.dy = 200 + (rand.nextFloat() * 10);
			}
		}
		
		private Point2D.Float getRandomPoint(){
			switch (shape) {
			case RECTANGLE:
				return new Point2D.Float(x + (rand.nextFloat() * width), y + (rand.nextFloat() * height));
			case CIRCLE:
				double angle = rand.nextFloat() * Math.PI * 2;
				return new Point2D.Float((float)(Math.cos(angle) * (rand.nextFloat() * radius)), (float)(Math.sin(angle) * (rand.nextFloat() * radius)));
			default:
				Game.log.logError("Unknown particle emission shape " + shape, "ParticleSystem", "Emission", "ParticleEmission");
				return null;
			}
		}
	}
}
