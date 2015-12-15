package game.UI;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class UI{
	
	//TODO: UIPainter
	
	// JAVADOC: UIPainter
	
	private Rectangle area;
	
	private Graphics2D translatedGraphics;
	
	private CopyOnWriteArrayList<UIElement> UIElements;
	
	/**
	 * @param area 
	 * @param elements
	 */
	public UI(Rectangle area, UIElement ... elements){
		this.area = area;
		UIElements = new CopyOnWriteArrayList<UIElement>(elements);
	}
	
	/**
	 * @param element
	 * @return 
	 */
	public boolean addUIElement(UIElement element){
		return UIElements.add(element);
	}
	
	/**
	 * @param element
	 * @return 
	 */
	public boolean removeUIElement(UIElement element){
		return UIElements.remove(element);
	}
	
	/**
	 * @param g2d
	 */
	public void paint(Graphics2D g2d) {
		translatedGraphics = (Graphics2D) g2d.create(area.x, area.y, area.width, area.height);
		for(UIElement element : UIElements){
			element.paint(translatedGraphics);
		}
	}
}
