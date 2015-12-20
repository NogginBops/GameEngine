package game.UI.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SolidBorder extends Border {

	public SolidBorder(int width) {
		super(width);
	}

	public SolidBorder(int width, Color color) {
		super(width, color);
	}

	@Override
	public void paint(Graphics2D g2d, Rectangle area) {
		g2d.setColor(color);
		g2d.fillRect(area.x, area.y, area.width, top);
		g2d.fillRect(area.x, area.height - bottom, area.width, bottom);
		g2d.fillRect(area.x, area.y, left, area.height);
		g2d.fillRect(area.width - right, area.y, right, area.height);
	}
}
