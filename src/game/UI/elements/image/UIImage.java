package game.UI.elements.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class UIImage extends UIElement {
	
	//JAVADOC: UIImage
	
	protected BufferedImage image;
	
	/**
	 * @param image
	 * @param x
	 * @param y
	 */
	public UIImage(BufferedImage image, float x, float y) {
		super(x, y, image.getWidth(), image.getHeight());
		this.image = image;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
	 */
	public UIImage(float x, float y, float width, float height, BufferedImage image){
		super(x, y, width, height);
		this.image = image;
	}
	
	/**
	 * 
	 */
	public void setNativeSize(){
		if(image != null){
			setSize(image.getWidth(), image.getHeight());
		}
	}
	
	/**
	 * 
	 * @param image
	 */
	public void setImage(BufferedImage image){
		this.image = image;
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(image != null){
			g2d.drawImage(image, (int)transform.getX(), (int)transform.getY(), (int)transform.getWidth(), (int)transform.getHeight(), null);
		}else{
			g2d.setColor(Color.WHITE);
			g2d.fill(transform.getTransformedRect());
		}
	}
	
	@Override
	public BufferedImage getImage() {
		return image;
	}
}
