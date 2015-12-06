package game.UI;

import game.gameObject.graphics.Painter;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIPainter extends Painter{
	
	//TODO: UIPainter
	
	// JAVADOC: UIPainter
	
	CopyOnWriteArrayList<UIElement> UIElements;
	
	public UIPainter(){
		UIElements = new CopyOnWriteArrayList<UIElement>();
	}
	
	public void addUIElement(UIElement element){
		UIElements.add(element);
	}
	
	public void paint(Graphics2D g2d) {
		
	}
}
