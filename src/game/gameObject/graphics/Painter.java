package game.gameObject.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import game.gameObject.BasicGameObject;
import game.screen.ScreenRect;

/**
 * Extended to make a object that can be used to paint graphics using a
 * Graphics2D object.
 * 
 * @version 1.0
 * @author Julius Häger
 * 
 */
public abstract class Painter extends BasicGameObject {
	
	/**
	 * The paintables the painter will paint when the {@link #paint(Graphics2D)}
	 * method is called.
	 */
	protected CopyOnWriteArrayList<Paintable> paintables;
	
	/*
	/**
	 * The bounds of the painter. Any {@link Paintable} outside of these bounds
	 * will not be painted.
	 */
	
	//protected ArrayList<Iamg>
	
	/**
	 * The area of the screen this painter is going to cover. Defaults to (0, 0, 1, 1)
	 */
	protected ScreenRect screenRectange = new ScreenRect(0, 0, 1, 1);
	
	/**
	 * The image that is going to be drawn to the screen.
	 */
	protected BufferedImage image;
	
	private Graphics2D translatedGraphics;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public Painter(float x, float y, int width, int height, int zOrder) {
		super(x, y, width, height, zOrder);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
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
	 * @deprecated
	 * 		Use getImage
	 */
	public void paint(Graphics2D g2d) {
		if (paintables != null && paintables.size() > 0) {
			translatedGraphics = (Graphics2D) g2d.create();
			translatedGraphics.translate(-bounds.x, -bounds.y);
			for (Paintable paintable : paintables) {
				if (paintable.getBounds().intersects(bounds)) {
					paintable.paint(translatedGraphics);
				}
			}
			translatedGraphics.dispose();
		}
	}
	
	/**
	 * 
	 * @param rect
	 */
	public void setScreenRectangle(ScreenRect rect) {
		screenRectange = rect;
	}
	
	/**
	 * @return
	 */
	public ScreenRect getScreenRectangle(){
		return screenRectange;
	}
	
	/**
	 * @return
	 */
	public BufferedImage getImage(){
		if (paintables != null && paintables.size() > 0) {
			if(translatedGraphics == null){
				translatedGraphics = image.createGraphics();
			}
			translatedGraphics.translate(-bounds.x, -bounds.y);
			for (Paintable paintable : paintables) {
				if (paintable.getBounds().intersects(bounds)) {
					paintable.paint(translatedGraphics);
				}
			}
			translatedGraphics.translate(bounds.x, bounds.y);
		}
		return image;
	}
}
