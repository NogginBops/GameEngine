package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.UI.elements.UIElement;
import game.gameObject.transform.Transform;
import game.util.math.vector.Vector2D;

/**
 * @author Julius Häger
 *
 */
public class BasicUIContainer extends UIContainer {
	
	//JAVADOC: BasicUIContainer
	
	protected Color color = Color.GRAY;	

	/**
	 * @param parent 
	 */
	public BasicUIContainer(Transform<UIElement> parent) {
		super(parent, new Vector2D(), new Vector2D(), 0);
		
		transform.setParent(parent);
	}
	
	/**
	 * @param area
	 * @param elements
	 */
	public BasicUIContainer(Rectangle2D area){
		super(area, null);
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(float width, float height) {
		super(0, 0, width, height, null);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(float x, float y, float width, float height) {
		super(x, y, width, height, null);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param elements
	 */
	public BasicUIContainer(float x, float y, float width, float height, UIElement...elements) {
		super(x, y, width, height, elements);
	}
	
	/**
	 * @param color
	 */
	public void setBackgroundColor(Color color){
		this.color = color;
	}
	
	/**
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	// TODO: Draw children!
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(transform.getTransformedRect());
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
