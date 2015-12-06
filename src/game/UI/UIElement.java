package game.UI;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;

public class UIElement implements Paintable{
	
	//TODO: UIElement
	
	// JAVADOC: UIElement

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void updateBounds() {
		
	}

	@Override
	public int getZOrder() {
		return 0;
	}

	@Override
	public int compareTo(GameObject object) {
		return 0;
	}

	@Override
	public void paint(Graphics2D g2d) {
		
	}
}
