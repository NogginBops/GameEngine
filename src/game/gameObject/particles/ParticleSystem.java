package game.gameObject.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicMovable;

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
	}

	@Override
	public void paint(Graphics2D g2d) {
		for (int i = 0; i < particles.length; i++) {
			//Paint
			BufferedImage image = getParticleImage(particles[i]);
			
			if(image == null){
				g2d.setColor(Color.magenta);
				g2d.drawRect((int)particles[i].x, (int)particles[i].y, (int)particles[i].width, (int)particles[i].height);
			}else{
				g2d.drawImage(image, (int)particles[i].x, (int)particles[i].y, null);
			}
		}
	}

	private BufferedImage getParticleImage(Particle particle) {
		if(imageMap.containsKey(particle.image)){
			if(imageMap.get(particle).containsKey(particle.color)){
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
			//Move
		}
	}
}
