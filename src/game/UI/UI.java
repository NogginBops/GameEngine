package game.UI;

import java.awt.Rectangle;

import game.UI.elements.UIElement;
import game.UI.elements.containers.UIContainer;
import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class UI extends UIContainer implements Paintable {

	// TODO: UIPainter?
	
	// TODO: UI position anchors

	// JAVADOC: UIPainter
	
	/**
	 * @param area
	 * @param elements
	 */
	public UI(Rectangle area, UIElement... elements) {
		super(elements);
		setBorder(null);
		this.area = area;
	}
	
	boolean result;
	
	/**
	 * @param element
	 * @return
	 */
	@Override
	public boolean addUIElement(UIElement element) {
		result = children.add(element);
		element.setRoot(this);
		element.setParent(this);
		sortChildren();
		return result;
	}

	@Override
	public Rectangle getBounds() {
		return area;
	}

	@Override
	public void updateBounds() {

	}

	@Override
	public int compareTo(GameObject object) {
		return getZOrder() - object.getZOrder();
	}

	@Override
	public int getZOrder() {
		return super.getZOrder();
	}
}
