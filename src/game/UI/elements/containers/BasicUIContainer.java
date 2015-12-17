package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;

import game.UI.elements.UIElement;

public class BasicUIContainer extends UIContainer {
	
	protected Color color = Color.GRAY;	

	public BasicUIContainer(UIContainer container) {
		super();
		area = container.getContainerArea();
		computeContainerArea();
	}

	public BasicUIContainer(int width, int height) {
		super(width, height);
	}

	public BasicUIContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public BasicUIContainer(int x, int y, int width, int height, UIElement... elements) {
		super(x, y, width, height, elements);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(containedArea);
		super.paint(g2d);
	}
}
