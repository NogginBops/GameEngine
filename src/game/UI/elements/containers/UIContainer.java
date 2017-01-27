package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import game.UI.border.Border;
import game.UI.elements.UIElement;
import game.gameObject.transform.Transform;
import game.util.math.vector.Vector2D;

/**
 * @author Julius Häger
 *
 */
public abstract class UIContainer extends UIElement {
	
	//JAVADOC: UIContainer
	
	//TODO: Implement basic painting of UIContainers and their children.
	
	protected Graphics2D translatedGraphics;

	protected Border border;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param elements 
	 */
	public UIContainer(float x, float y, float width, float height, UIElement[] elements) {
		super(x, y, width, height);
		
		if (elements != null) {
			for (UIElement uiElement : elements) {
				addChild(uiElement);
			}
		}
	}
	
	/**
	 * @param rect
	 * @param elements
	 */
	public UIContainer(Rectangle2D rect, UIElement[] elements){
		super((float)rect.getX(), (float)rect.getY(), (float)rect.getWidth(), (float)rect.getHeight());
		
		if (elements != null) {
			for (UIElement uiElement : elements) {
				addChild(uiElement);
			}
		}
	}
	
	/**
	 * @param parent
	 * @param pos 
	 * @param size 
	 * @param zOrder
	 */
	public UIContainer(Transform<UIElement> parent, Vector2D pos, Vector2D size, int zOrder) {
		super(pos.x, pos.y, size.x, size.y, zOrder);
		
		transform.setParent(parent);
		
		root = parent.getObject().getRoot();
	}
	
	/**
	 * 
	 */
	public void sortChildren(){
		transform.getChildren().sort((e, e2) -> e.getObject().getZOrder() - e2.getObject().getZOrder());
	}
	
	/**
	 * @param element
	 */
	public void addChild(UIElement element){
		transform.addChild(element.getTransform());
	}
	
	/**
	 * @param elements
	 */
	public void addChildren(UIElement...elements){
		for (UIElement uiElement : elements) {
			addChild(uiElement);
		}
	}
	
	/**
	 * @param child
	 */
	public void addChild(Transform<UIElement> child) {
		transform.addChild(transform);
	}
	
	/**
	 * @param element
	 */
	public void removeChild(UIElement element){
		transform.removeChild(element.getTransform());
	}
	
	/**
	 * @param child
	 */
	public void removeChild(Transform<UIElement> child){
		transform.removeChild(child);
	}

	/**
	 * @return
	 */
	public Border getBorder() {
		return border;
	}

	/**
	 * @param border
	 */
	public void setBorder(Border border) {
		this.border = border;
	}
	
	/**
	 * @param width
	 */
	public void setBorderSize(int width) {
		border.setWidth(width);
	}

	/**
	 * @param color
	 */
	public void setBorderColor(Color color) {
		border.setColor(color);
	}
	
	
	/**
	 * @param element
	 * @return
	 */
	public boolean contains(UIElement element){
		for (UIElement uiElement : getChildern()) {
			if(uiElement == element){
				return true;
			}else if(uiElement instanceof UIContainer){
				if(((UIContainer)uiElement).contains(element)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public CopyOnWriteArrayList<UIElement> getChildern() {
		return transform.getChildren().stream().map((t) -> t.getObject()).collect(Collectors.toCollection(() -> new CopyOnWriteArrayList<>()));
	}
}
