package game.UI;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.UI.elements.UIElement;
import game.UI.elements.containers.UIContainer;
import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;

/**
 * @author Julius H�ger
 * @version 1.0
 */
public class UI extends UIContainer implements Paintable {
	
	// TODO: UI position anchors
	
	//TODO: XML application UI (Kind of like HTML)
	//UI scripting?

	// JAVADOC: UIPainter
	
	private boolean active = true;
	
	/**
	 * @param area
	 * @param elements
	 */
	public UI(Rectangle2D.Float area, UIElement... elements) {
		super(area, elements);
		setBorder(null);
		root = this;
		//this.area = area;
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
	public Rectangle2D.Float getBounds() {
		return area;
	}

	@Override
	public void updateBounds() {
		//TODO: Fix UI bounds!
	}
	
	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int compareTo(GameObject object) {
		return getZOrder() - object.getZOrder();
	}

	@Override
	public int getZOrder() {
		return super.getZOrder();
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		super.paint(g2d);
	}

	@Override
	public BufferedImage getImage() {
		//TODO: Add support for pre-rendered UI
		return null;
	}
}
