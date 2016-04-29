package game.UI.elements.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

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
	public UIImage(Image image, int x, int y) {
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
	public UIImage(int x, int y, int width, int height, Image image){
		super(x, y, width, height);
		this.image = image;
	}
	
	/**
	 * 
	 */
	public void setNativeSize(){
		if(image != null){
			area.setSize(image.getWidth(null), image.getHeight(null));
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(image != null){
			g2d.drawImage(image, area.x, area.y, area.width, area.height, null);
		}else{
			g2d.setColor(Color.WHITE);
			g2d.fill(area);
		}
	}
}
