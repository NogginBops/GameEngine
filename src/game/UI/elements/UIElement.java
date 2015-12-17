package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * 
 * @author Julius Häger
 */
public abstract class UIElement {

	// TODO: UIElement

	// JAVADOC: UIElement

	protected Rectangle area;

	protected int zOrder = 10;

	public UIElement() {
		area = new Rectangle();
	}

	public UIElement(Rectangle area) {
		this.area = area;
	}

	public UIElement(int width, int height) {
		area = new Rectangle(width, height);
	}

	public int getZOrder() {
		return zOrder;
	}

	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

}
