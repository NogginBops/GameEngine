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
		super(container.getContainedArea());
		//area = container.getContainerArea();
		computeContainedArea();
		
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
	public BasicUIContainer(UIContainer container, float insetLeft, float insetTop, float insetRight, float insetBottom) {
		super();
<<<<<<< HEAD
		area = container.getContainerArea();
		area.setRect(area.getX() + insetLeft, area.getY() + insetTop, area.getWidth() - (insetLeft + insetRight), area.getHeight() - (insetTop - insetBottom));
		computeContainerArea();
=======
		area = container.getContainedArea();
		area.setRect(area.x + insetLeft, area.y + insetTop, area.width - (insetLeft + insetRight), area.height - (insetTop - insetBottom));
		computeContainedArea();
>>>>>>> origin/GameEngine(Nightly)
		
		setParent(container);
	}

	/**
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(float width, float height) {
		super(width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BasicUIContainer(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param elements
	 */
	public BasicUIContainer(float x, float y, float width, float height, UIElement... elements) {
		super(x, y, width, height, elements);
	}
	
	/**
	 * @param color
	 */
	public void setBackgroundColor(Color color){
		this.color = color;
	}
	
	/**
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(containedArea);
		super.paint(g2d);
	}
}
