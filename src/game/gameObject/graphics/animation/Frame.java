package game.gameObject.graphics.animation;

import java.awt.image.BufferedImage;

/**
 * @author Julius Häger
 *
 */
public class Frame {
	
	/**
	 * 
	 */
	public final BufferedImage image;
	
	/**
	 * 
	 */
	public final float time;
	
	/**
	 * @param image
	 * @param time
	 */
	public Frame(BufferedImage image, float time) {
		this.image = image;
		this.time = time;
	}
	
	/**
	 * @param frame
	 * @param time
	 */
	public Frame(Frame frame, float time) {
		image = frame.image;
		this.time = time;
	}
	
}
