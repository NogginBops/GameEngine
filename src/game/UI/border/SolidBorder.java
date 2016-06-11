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
	public void paint(Graphics2D g2d, Rectangle2D.Float area) {
		g2d.setColor(color);
		g2d.fillRect((int)area.x, (int)area.y, (int)area.width, top);
		g2d.fillRect((int)area.x, (int)(area.y + area.height - bottom), (int)area.width, bottom);
		g2d.fillRect((int)area.x, (int)area.y, left, (int)area.height);
		g2d.fillRect((int)(area.x + area.width - right), (int)area.y, right, (int)area.height);
	}
}
