package game.UI.elements.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class UIImage extends UIElement {
	
	//JAVADOC: UIImage
	
	protected Image image;
	
	/**
	 * @param image
	 * @param x
	 * @param y
	 */
	public UIImage(Image image, float x, float y) {
		super(x, y, image.getWidth(null), image.getHeight(null));
		this.image = image;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
	 */
	public UIImage(float x, float y, float width, float height, Image image){
		super(x, y, width, height);
		this.image = image;
	}
	
	/**
	 * 
	 */
	public void setNativeSize(){
		if(image != null){
			setSize(image.getWidth(null), image.getHeight(null));
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
			g2d.drawImage(image, (int)area.getX(), (int)area.getY(), (int)area.getWidth(), (int)area.getHeight(), null);
		}else{
			g2d.setColor(Color.WHITE);
			g2d.fill(area);
		}
	}
}
