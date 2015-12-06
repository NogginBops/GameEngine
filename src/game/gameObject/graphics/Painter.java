package game.gameObject.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Extended to make a object that can be used to paint graphics using a
 * Graphics2D object.
 * 
 * @version 1.0
 * @author Julius Häger
 * 
 */
public abstract class Painter {

	/**
	 * The paintables the painter will paint when the {@link #paint(Graphics2D)}
	 * method is called.
	 */
	protected CopyOnWriteArrayList<Paintable> paintables;

	/**
	 * The bounds of the painter. Any {@link Paintable} outside of these bounds
	 * will not be painted.
	 */
	protected Rectangle bounds;
	
	/**
	 * <p>
	 * Called to paint to a Graphics2D object.
	 * </p>
	 * <p>
	 * Subclasses overriding this method should only update the
	 * {@link #paintables} list before calling the super method.
	 * </p>
	 * 
	 * @param g2d
	 *            the Graphics2D object to paint to
	 */
	public void paint(Graphics2D g2d) {
		if(paintables != null && paintables.size() > 0){
			Graphics2D translatedGraphics = (Graphics2D)g2d.create();
			translatedGraphics.translate(-bounds.x, -bounds.y);
				for (Paintable paintable : paintables) {
					if (paintable.getBounds().intersects(bounds)) {
						paintable.paint(translatedGraphics);
					}
				}
			translatedGraphics.dispose();
		}
	}
}
