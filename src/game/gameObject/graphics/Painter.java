package game.gameObject.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import game.gameObject.BasicGameObject;
import game.gameObject.physics.PhysicsEngine;
import game.screen.ScreenRect;
import game.util.image.ImageUtils;

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
	protected CopyOnWriteArrayList<Paintable> paintables = new CopyOnWriteArrayList<>();
	
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
	
	protected Graphics2D translatedGraphics;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public Painter(float x, float y, float width, float height, int zOrder) {
		super(x, y, width, height, zOrder);
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
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
			translatedGraphics.transform(transform.getAffineTransform());
			for (Paintable paintable : paintables) {
				if(PhysicsEngine.collides(paintable.getBounds(), getBounds())){
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
	 * 
	 */
	public int drawnObjects = 0;
	int tempDrawnObjects = 0;
	
	BufferedImage paintableImage;
	
	//JAVADOC: Painter
	
	/**
	 * @return
	 */
	public BufferedImage getImage(){
		tempDrawnObjects = 0;
		
		if (paintables != null && paintables.size() > 0) {
			for (Paintable paintable : paintables) {
				if(paintable.isActive()){
					if(PhysicsEngine.collides(paintable.getBounds(), getBounds())){
						paintableImage = paintable.getImage();
						if(paintableImage != null){
							translatedGraphics.drawImage(paintableImage,
									0, 0, (int)paintable.getBounds().getWidth(),
									(int)paintable.getBounds().getHeight(), null);
						}else{
							paintable.paint(translatedGraphics);
						}
						
						tempDrawnObjects++;
					}
				}
			}
		}
		

		drawnObjects = tempDrawnObjects;
		
		return image;
		
		/*
		if (paintables != null && paintables.size() > 0) {
			if(translatedGraphics == null){
				translatedGraphics = image.createGraphics();
				originalTransform = translatedGraphics.getTransform();
			}
			
			//TODO: Find better way to handle this
			//translatedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			AffineTransform originalAT = transform.getAffineTransform();
			originalAT.setToTranslation(-originalAT.getTranslateX(), -originalAT.getTranslateY());
			translatedGraphics.transform(originalAT);
			for (Paintable paintable : paintables) {
				if(paintable.isActive()){
					if(PhysicsEngine.collides(paintable.getBounds(), getBounds())){
						Transform paintableTransform = paintable.getTransform();
						translatedGraphics.transform(paintableTransform.getAffineTransform());
						
						paintableImage = paintable.getImage();
						if(paintableImage != null){
							translatedGraphics.drawImage(paintableImage,
									0, 0, (int)paintable.getBounds().getWidth(),
									(int)paintable.getBounds().getHeight(), null);
						}else{
							paintable.paint(translatedGraphics);
						}
						
						translatedGraphics.setTransform(originalAT);
						
						tempDrawnObjects++;
					}
				}
			}
			translatedGraphics.setTransform(originalTransform);
		}
		
		drawnObjects = tempDrawnObjects;
		
		return image;
		*/
	}
}
