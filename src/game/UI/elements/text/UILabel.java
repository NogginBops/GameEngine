package game.UI.elements.text;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.UI.elements.UIElement;

public class UILabel extends UIElement {
	
	//TODO: Manage go get width and height of the label
	
	//JAVADOC: UILable

	protected String text;

	protected Color color = Color.WHITE;

	private FontMetrics fontMetrics;

	public UILabel(String text) {
		super();
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2d) {
		fontMetrics = g2d.getFontMetrics();
		area.setSize(fontMetrics.stringWidth(text), fontMetrics.getHeight());
		g2d.setColor(color);
		g2d.drawString(text, area.x, area.y + fontMetrics.getHeight());
	}
}
