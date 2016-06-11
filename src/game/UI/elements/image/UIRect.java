package game.UI.elements.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class UIRect extends UIElement {
	
	//JAVADOC: UIRect

	protected Color color = Color.LIGHT_GRAY;
	
	/**
	 * 
	 */
	public UIRect() {
		super();
	}
	
	/**
	 * @param color
	 */
	public UIRect(Color color) {
		super();
		this.color = color;
	}
	
	/**
	 * @param rect
	 */
	public UIRect(Rectangle2D.Float rect) {
		super(rect);
	}
	
	/**
	 * @param rect
	 * @param color
	 */
	public UIRect(Rectangle2D.Float rect, Color color) {
		super(rect);
		this.color = color;
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public UIRect(float width, float height) {
		super(width, height);
	}
	
	/**
	 * @param width
	 * @param height
	 * @param color
	 */
	public UIRect(float width, float height, Color color) {
		super(width, height);
		this.color = color;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public UIRect(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public UIRect(float x, float y, float width, float height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}
	
	/**
	 * @param color
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(area);
	}
}
