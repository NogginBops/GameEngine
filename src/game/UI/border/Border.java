package game.UI.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author Julius Häger
 * @version 1.0
 */
public abstract class Border {
	
	//JAVADOC: Border

	/**
	 * The width of the border in that direction
	 */
	protected int top = 5, bottom = 5, left = 5, right = 5;
	
	/**
	 * The color of the border
	 */
	protected Color color = Color.BLACK;

	/**
	 * Creates a default black border of 5 pixels
	 */
	public Border() {
		top = bottom = left = right = 5;
	}
	
	/**
	 * Creates a black border with the specified width in all directions
	 * @param width
	 */
	public Border(int width) {
		top = bottom = left = right = width;
	}
	
	/**
	 * Creates a border with the specified width in all directions and a specified color;
	 * @param width
	 * @param color
	 */
	public Border(int width, Color color) {
		top = bottom = left = right = width;
		this.color = color;
	}

	/**
	 * Paints the border in the specified area.
	 * 
	 * @param g2d
	 * @param area
	 */
	public abstract void paint(Graphics2D g2d, Rectangle area);

	/**
	 * Returns a Rectangle that has had the border area removed
	 * @param rect
	 * @return
	 */
	public Rectangle getInnerArea(Rectangle rect) {
		//TODO: Pre compute?
		return new Rectangle(rect.x + left, rect.y + top, rect.width - right - left, rect.height - bottom - top);
	}

	/**
	 * Gets the top border width
	 * @return
	 */
	public int getTop() {
		return top;
	}
	
	/**
	 * Sets the top border width
	 * @param top
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * Gets the bottom border width
	 * @return
	 */
	public int getBottom() {
		return bottom;
	}
	
	/**
	 * Sets the bottom border width
	 * @param bottom
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	
	/**
	 * Gets the left border width
	 * @return
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Sets the left border width
	 * @param left
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * Gets the right border width
	 * @return
	 */
	public int getRight() {
		return right;
	}

	/**
	 * Sets the right border width
	 * @param right
	 */
	public void setRight(int right) {
		this.right = right;
	}
	
	/**
	 * Sets the border width in all directions
	 * @param width
	 */
	public void setWidth(int width) {
		top = bottom = left = right = width;
	}
	
	/**
	 * Sets the color of the border
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets the color of the border
	 * @return
	 */
	public Color getColor() {
		return color;
	}
}
