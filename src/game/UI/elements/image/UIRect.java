package game.UI.elements.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
	public UIRect(Rectangle rect) {
		super(rect);
	}
	
	/**
	 * @param rect
	 * @param color
	 */
	public UIRect(Rectangle rect, Color color) {
		super(rect);
		this.color = color;
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public UIRect(int width, int height) {
		super(width, height);
	}
	
	/**
	 * @param width
	 * @param height
	 * @param color
	 */
	public UIRect(int width, int height, Color color) {
		super(width, height);
		this.color = color;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public UIRect(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public UIRect(int x, int y, int width, int height, Color color) {
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
