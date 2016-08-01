package game.UI.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class SolidBorder extends Border {
	
	//JAVADOC: SolidBorder

	/**
	 * Creates a black solid border with the specified width
	 * @param width
	 */
	public SolidBorder(int width) {
		super(width);
	}

	/**
	 * Creates a solid border with the specified width and color
	 * @param width
	 * @param color
	 */
	public SolidBorder(int width, Color color) {
		super(width, color);
	}

	@Override
	public void paint(Graphics2D g2d, Rectangle2D area) {
		g2d.setColor(color);
		g2d.fillRect((int)area.getX(), (int)area.getY(), (int)area.getWidth(), top);
		g2d.fillRect((int)area.getX(), (int)(area.getY() + area.getHeight() - bottom), (int)area.getWidth(), bottom);
		g2d.fillRect((int)area.getX(), (int)area.getY(), left, (int)area.getHeight());
		g2d.fillRect((int)(area.getX() + area.getWidth() - right), (int)area.getY(), right, (int)area.getHeight());
	}
}
