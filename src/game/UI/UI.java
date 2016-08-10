package game.UI;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.UI.elements.UIElement;
import game.UI.elements.containers.UIContainer;
import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;
import game.gameObject.transform.Transform;

/**
 * @author Julius Häger
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
	public UI(Rectangle2D area, UIElement... elements) {
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
	public Shape getShape() {
		return area;
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

	@Override
	public Transform getTransform() {
		Game.log.logError("UI is not implemented! Fix this!");
		return null;
	}
	
	@Override
	public void setTransform(Transform transform) {
		Game.log.logError("UI is not implemented! Fix this!");
	}
}
