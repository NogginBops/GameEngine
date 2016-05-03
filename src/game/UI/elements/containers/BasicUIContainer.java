package game.UI.elements.containers;

import java.awt.Color;
import java.awt.Graphics2D;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class BasicUIContainer extends UIContainer {
	
	//JAVADOC: BasicUIContainer
	
	protected Color color = Color.GRAY;	

	/**
	 * @param container
	 */
	//FIXME: This constructor is broken!!
	public BasicUIContainer(UIContainer container) {
		super(container.getContainerArea());
		//area = container.getContainerArea();
		computeContainerArea();
		
		setParent(container);
	}
	
	/**
	 * @param container
	 * @param insetLeft
	 * @param insetTop
	 * @param insetRight
	 * @param insetBottom
	 */
	//FIXME: This constructor is broken!!
	public BasicUIContainer(UIContainer container, int insetLeft, int insetTop, int insetRight, int insetBottom) {
		super();
		area = container.getContainerArea();
		area.setBounds(area.x + insetLeft, area.y + insetTop, area.width - (insetLeft + insetRight), area.height - (insetTop - insetBottom));
		computeContainerArea();
		
		setParent(container);
	}

	/**
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(int width, int height) {
		super(width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param elements
	 */
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
