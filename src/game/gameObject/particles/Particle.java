package game.gameObject.particles;

import java.awt.Color;

/**
 * @author Julius Häger
 *
 */
public class Particle {
	
	//JAVADOC: Particle
	
	//TODO: Should these be public or private/protected (Probably private)
	
	/**
	 * 
	 */
	public boolean active;
	
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
	public float dx;
	
	/**
	 * 
	 */
	public float dy;
	
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
	public Color color;
	
	/**
	 * A index to a image in the particle system
	 */
	public int image;
	
	
	/**
	 * @param x
	 * @param y
	 * @param width 
	 * @param height 
	 * @param size 
	 * @param color 
	 * @param image 
	 */
	public Particle(float x, float y, float width, float height, Color color, int image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.image = image;
		
		active = true;
		dx = 0;
		dy = 0;
	}

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}

	/**
	 * @param dx
	 */
	public void setDX(float dx){
		this.dx = dx;
	}
	
	/**
	 * @param dy
	 */
	public void setDY(float dy){
		this.dy = dy;
	}
	
	/**
	 * @param dx
	 * @param dy
	 */
	public void setVelocity(float dx , float dy){
		this.dx = dx;
		this.dy = dy;
	}
	
}
