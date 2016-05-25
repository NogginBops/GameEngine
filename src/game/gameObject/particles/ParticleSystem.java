package game.gameObject.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

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
	
	private Particle[] particles;
	
	private HashMap<Integer, HashMap<Color, BufferedImage>> imageMap;

	/**
	 * @param rect
	 * @param zOrder
	 * @param maxParticles 
	 */
	public ParticleSystem(Rectangle rect, int zOrder, int maxParticles) {
		super(rect, zOrder);
		particles = new Particle[maxParticles];
		imageMap = new HashMap<>();
	}

	@Override
	public void paint(Graphics2D g2d) {
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				//Paint
				BufferedImage image = getParticleImage(particles[i]);
				
				if(image == null){
					g2d.setColor(Color.magenta);
					g2d.drawRect((int)(particles[i].x), (int)(particles[i].y), (int)particles[i].width, (int)particles[i].height);
				}else{
					g2d.drawImage(image, (int)particles[i].x, (int)particles[i].y, (int)particles[i].width, (int)particles[i].height, null);
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
		
		for (int i = 0; i < particles.length; i++) {
			if(particles[i].active == true){
				particles[i].x = particles[i].x + (particles[i].dx * (timeNano / 1000000000f));
				particles[i].y = particles[i].y + (particles[i].dy * (timeNano / 1000000000f));
			
				if(MathUtils.isOutside(particles[i].x, x, x + (width - particles[i].width))!= 0 ||
						MathUtils.isOutside(particles[i].y, y, y + (height - particles[i].height))!= 0){
					
					//TODO: Different edge modes
					
					particles[i].x = MathUtils.clamp(particles[i].x, x, x + (width - particles[i].width));
					particles[i].y = MathUtils.clamp(particles[i].y, y, y + (height - particles[i].height));
					
					particles[i].dx = -particles[i].dx;
					particles[i].dy = -particles[i].dy;
				}
			}
		}
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
	 * @param imageID
	 * @param image
	 */
	public void addImage(int imageID, BufferedImage image){
		HashMap<Color, BufferedImage> tempMap = new HashMap<>();
		tempMap.put(Color.WHITE, image);
		imageMap.put(imageID, tempMap);
	}
}
