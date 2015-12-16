package game.UI.elements.text;

import java.awt.Graphics2D;

import game.UI.elements.UIElement;

public class UILabel extends UIElement {
	
	protected String text;
	
	public UILabel(String text) {
		super();
		this.text = text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawString(text, area.x, area.y);
	}
}
