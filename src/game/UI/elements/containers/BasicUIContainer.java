package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;

import game.UI.elements.UIElement;

public class BasicUIContainer extends UIContainer {
	
	//TODO: Proper border system
	
	protected int border = 2;
	
	protected Color borderColor = Color.white;
	
	public BasicUIContainer(UIContainer container) {
		super();
		area = container.getContainerArea();
		containedArea.setFrame(area.x + border, area.y + border, area.width - border * 2, area.height - border * 2);
	}
	
	public BasicUIContainer(int width, int height) {
		super();
		area.setFrame(0, 0, width, height);
		containedArea.setFrame(area.x + border, area.y + border, area.width - border * 2, area.height - border * 2);
	}
	
	public BasicUIContainer(int x, int y, int width, int height){
		super();
		area.setFrame(x, y, width, height);
		containedArea.setFrame(area.x + border, area.y + border, area.width - border * 2, area.height - border * 2);
	}
	
	public BasicUIContainer(int x, int y, int width, int height, UIElement ... elements){
		super(elements);
		area.setFrame(x, y, width, height);
		containedArea.setFrame(area.x + border, area.y + border, area.width - border * 2, area.height - border * 2);
	}
	
	public void setBorderSize(int width){
		border = width;
		containedArea.setFrame(area.x + border, area.y + border, area.width - border * 2, area.height - border * 2);
	}
	
	public void setBorderColor(Color color){
		borderColor = color;
	}
	
	Color tempColor;
	
	@Override
	public void paint(Graphics2D g2d) {
		tempColor = g2d.getColor();
		g2d.setColor(borderColor);
		g2d.fillRect(area.x, area.y, area.width, area.height);
		g2d.clearRect(containedArea.x, containedArea.y, containedArea.width, containedArea.height);
		g2d.setColor(tempColor);
		super.paint(g2d);
	}
}
