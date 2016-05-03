package game.UI.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
	public void paint(Graphics2D g2d, Rectangle area) {
		g2d.setColor(color);
		g2d.fillRect(area.x, area.y, area.width, top);
		g2d.fillRect(area.x, area.y + area.height - bottom, area.width, bottom);
		g2d.fillRect(area.x, area.y, left, area.height);
		g2d.fillRect(area.x + area.width - right, area.y, right, area.height);
	}
}
