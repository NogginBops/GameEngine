package game.UI.elements.containers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import game.UI.elements.UIElement;

public abstract class UIContainer extends UIElement {
	
	protected Rectangle containedArea;
	
	protected Graphics2D translatedGraphics;
	
	protected CopyOnWriteArrayList<UIElement> elements;
	
	public UIContainer(UIElement ... elements) {
		super();
		this.elements = new CopyOnWriteArrayList<UIElement>(elements);
		containedArea = new Rectangle();
	}
	
	public boolean addUIElement(UIElement element){
		return elements.add(element);
	}
	
	public boolean removeUIElement(UIElement element){
		return elements.remove(element);
	}

	@Override
	public void paint(Graphics2D g2d) {
		translatedGraphics = (Graphics2D) g2d.create(containedArea.x, containedArea.y, containedArea.width, containedArea.height);
		for(UIElement element : elements){
			element.paint(translatedGraphics);
		}
	}
	
	public Rectangle getContainerArea(){
		return containedArea;
	}
	
	public void setPos(int x, int y){
		area.setLocation(x, y);
	}
	
	public void setSize(int width, int height){
		this.area.setSize(width, height);
	}
}
