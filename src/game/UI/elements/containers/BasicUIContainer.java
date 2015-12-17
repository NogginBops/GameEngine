package game.UI.elements.containers;

import java.awt.Color;

import game.UI.elements.UIElement;

public class BasicUIContainer extends UIContainer {

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
}
