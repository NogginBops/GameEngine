package game.UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.UI.elements.containers.UIContainer;
import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicRotatable;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class UI extends BasicRotatable implements Paintable {
	
	// JAVADOC: UI
	
	// TODO: UI position anchors
	
	protected UIContainer mainContainer;
	
	/**
	 * @param x
	 * @param y
	 * @param zOrder
	 */
	public UI(float x, float y, int zOrder){
		super(x, y, 0, 0, zOrder, 0);
	}
	
	/**
	 * @param x 
	 * @param y 
	 * @param zOrder 
	 * @param container 
	 */
	public UI(float x, float y, int zOrder, UIContainer container) {
		super(x, y, 0, 0, zOrder, 0);
		
		mainContainer = container;
	}
	
	/**
	 * @param mainContainer
	 */
	public void setMainContainer(UIContainer mainContainer){
		this.mainContainer = mainContainer;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		mainContainer.paint(g2d);
	}

	@Override
	public BufferedImage getImage() {
		//TODO: Add support for pre-rendered UI
		return null;
	}
}
