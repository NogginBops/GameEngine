package game.gameObject.graphics;

import game.gameObject.GameObject;

import java.awt.Graphics2D;

/**
 * The Paintable interface provides functions for painting a element using a Graphics2D object as well as functions for Z-Ordering and checking bounds.
 * 
 * @version 1.0
 * @author Julius Häger
 *
 */
public interface Paintable extends GameObject{
	
	/**
	 * <p>Used by a {@link Painter} to draw the Paintable using the provided offsets</p>
	 * <p>
	 * <i>The offsets are subtracted</i>
	 * 
	 * @param g2d the current graphics object
	 */
	public void paint(Graphics2D g2d);
	
}