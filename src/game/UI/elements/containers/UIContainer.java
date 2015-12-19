package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import game.UI.UISorter;
import game.UI.border.Border;
import game.UI.border.SolidBorder;
import game.UI.elements.UIElement;

public abstract class UIContainer extends UIElement {

	protected Rectangle containedArea;

	protected Graphics2D translatedGraphics;

	protected CopyOnWriteArrayList<UIElement> children;

	protected Border border;

	public UIContainer(UIElement... elements) {
		super();
		this.children = new CopyOnWriteArrayList<UIElement>(elements);
		sortChildren();
		containedArea = new Rectangle();
		border = new SolidBorder(5);
	}
	
	public UIContainer(int width, int height, UIElement ... elements){
		super(width, height);
		this.children = new CopyOnWriteArrayList<UIElement>(elements);
		sortChildren();
		border = new SolidBorder(5);
		computeContainerArea();
	}
	
	public UIContainer(int x, int y, int width, int height, UIElement ... elements){
		super(x, y, width, height);
		this.children = new CopyOnWriteArrayList<UIElement>(elements);
		sortChildren();
		border = new SolidBorder(5);
		computeContainerArea();
	}
	
	public UIContainer(int x, int y, int width, int height, Border border, UIElement ... elements){
		super(x, y, width, height);
		this.children = new CopyOnWriteArrayList<UIElement>(elements);
		sortChildren();
		this.border = border;
		computeContainerArea();
	}
	
	public void sortChildren(){
		children.sort(UISorter.instance);
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
		computeContainerArea();
	}
	
	public void setBorderSize(int width) {
		border.setWidth(width);
		computeContainerArea();
	}

	public void setBorderColor(Color color) {
		border.setColor(color);
		computeContainerArea();
	}
	
	boolean result;
	public boolean addUIElement(UIElement element) {
		result = children.add(element);
		sortChildren();
		return result;
	}

	public boolean removeUIElement(UIElement element) {
		sortChildren();
		return children.remove(element);
	}

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
		border.paint(g2d, area);
		translatedGraphics = (Graphics2D) g2d.create(containedArea.x, containedArea.y, containedArea.width,
				containedArea.height);
		for (UIElement element : children) {
			element.paint(translatedGraphics);
		}
	}

	/**
	 * <b>Note:</b><br>
	 * This method updates the containedArea member variable.
	 * @return
	 */
	protected Rectangle computeContainerArea() {
		return containedArea = border.getInnerArea(area);
	}

	public Rectangle getContainerArea() {
		return containedArea;
	}

	public void setPos(int x, int y) {
		area.setLocation(x, y);
	}

	public void setSize(int width, int height) {
		this.area.setSize(width, height);
	}
}
