package game.gameObject.particles;

import java.awt.geom.Point2D;
import java.util.Random;

import game.Game;

/**
 * @author Julius Häger
 *
 */
public class ParticleEmitter{
	
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
	
	//TODO: Figure out what members that should be exposed
	
	/**
	 * 
	 */
	public boolean enabled = true;
	
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
	public ParticleCustomizer customizer;
	
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
			
			if(p == null){
				//Cannot spawn any more particles
				break;
			}
			
			//Customize particles
			if(customizer != null){
				customizer.customize(p);
			}
		}
	}
	
	private Point2D.Float getRandomPoint(){
		switch (shape) {
		case RECTANGLE:
			return new Point2D.Float(x + (rand.nextFloat() * width), y + (rand.nextFloat() * height));
		case CIRCLE:
			double angle = rand.nextFloat() * Math.PI * 2;
			return new Point2D.Float((float)(x + (Math.cos(angle) * (rand.nextFloat() * radius))), (float)(y + (Math.sin(angle) * (rand.nextFloat() * radius))));
		default:
			Game.log.logError("Unknown particle emission shape " + shape, "ParticleSystem", "Emission", "ParticleEmission");
			return null;
		}
	}
}
