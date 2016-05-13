package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import game.UI.UISorter;
import game.UI.border.Border;
import game.UI.border.SolidBorder;
import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public abstract class UIContainer extends UIElement {
	
	//JAVADOC: UIContainer

	protected Rectangle containedArea;

	protected Graphics2D translatedGraphics;

	protected CopyOnWriteArrayList<UIElement> children;

	protected Border border;

	/**
	 * @param elements
	 */
	public UIContainer(UIElement... elements) {
		super();
		this.children = new CopyOnWriteArrayList<UIElement>();
		addUIElements(elements);
		containedArea = new Rectangle();
		border = new SolidBorder(5);
	}
	
	/**
	 * @param rect
	 * @param elements
	 */
	public UIContainer(Rectangle rect, UIElement... elements) {
		super(rect);
		this.children = new CopyOnWriteArrayList<UIElement>();
		addUIElements(elements);
		containedArea = new Rectangle();
		border = new SolidBorder(5);
	}
	
	/**
	 * @param width
	 * @param height
	 * @param elements
	 */
	public UIContainer(int width, int height, UIElement ... elements){
		super(width, height);
		this.children = new CopyOnWriteArrayList<UIElement>();
		addUIElements(elements);
		border = new SolidBorder(5);
		computeContainerArea();
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param elements
	 */
	public UIContainer(int x, int y, int width, int height, UIElement ... elements){
		super(x, y, width, height);
		this.children = new CopyOnWriteArrayList<UIElement>();
		addUIElements(elements);
		border = new SolidBorder(5);
		computeContainerArea();
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param border
	 * @param elements
	 */
	public UIContainer(int x, int y, int width, int height, Border border, UIElement ... elements){
		super(x, y, width, height);
		this.children = new CopyOnWriteArrayList<UIElement>();
		addUIElements(elements);
		this.border = border;
		computeContainerArea();
	}
	
	/**
	 * 
	 */
	public void sortChildren(){
		children.sort(UISorter.instance);
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
		computeContainerArea();
	}
	
	/**
	 * @param width
	 */
	public void setBorderSize(int width) {
		border.setWidth(width);
		computeContainerArea();
	}

	/**
	 * @param color
	 */
	public void setBorderColor(Color color) {
		border.setColor(color);
		computeContainerArea();
	}
	
	boolean result;
	
	/**
	 * @param element
	 * @return
	 */
	public boolean addUIElement(UIElement element) {
		result = children.add(element);
		element.setRoot(root);
		element.setParent(this);
		sortChildren();
		return result;
	}
	
	/**
	 * @param elements
	 */
	public void addUIElements(UIElement ... elements){
		for (UIElement element : elements) {
			addUIElement(element);
		}
	}

	/**
	 * @param element
	 * @return
	 */
	public boolean removeUIElement(UIElement element) {
		sortChildren();
		element.setParent(null);
		element.setRoot(null);
		return children.remove(element);
	}
	
	/**
	 * @param element
	 * @return
	 */
	public boolean contains(UIElement element){
		for (UIElement uiElement : children) {
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
		return children;
	}

	/**
	 * 
	 * 
	 * 
	 * <b>Note:</b><br>
	 * When overriding this method calling the super method will cause all the
	 * children to be drawn. <br>
	 * Often times you want to call this super the last thing you do in the
	 * paint method.
	 */
	@Override
	public void paint(Graphics2D g2d) {
		computeContainerArea();
		if(border != null){
			border.paint(g2d, area);
		}
		if(children.size() > 0){
			translatedGraphics = (Graphics2D) g2d.create(containedArea.x, containedArea.y, containedArea.width,
					containedArea.height);
			for (UIElement element : children) {
				element.paint(translatedGraphics);
			}
		}
	}

	/**
	 * <b>Note:</b><br>
	 * This method updates the containedArea member variable.
	 * @return
	 */
	protected Rectangle computeContainerArea() {
		if(border != null){
			return containedArea = border.getInnerArea(area);
		}else{
			return containedArea = area;
		}
	}

	/**
	 * @return
	 */
	public Rectangle getContainerArea() {
		return containedArea;
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle parentArea = parent.getBounds();
		computeContainerArea();
		return new Rectangle(containedArea.x + parentArea.x, containedArea.y + parentArea.y, containedArea.width, containedArea.height);
	}

	
}
