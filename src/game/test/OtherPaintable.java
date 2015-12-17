package game.test;

import game.gameObject.graphics.Building;
import game.gameObject.graphics.Paintable;

import java.awt.Color;
import java.awt.Graphics2D;

public class OtherPaintable extends Building implements Paintable {

	private Color color;

	public OtherPaintable(int x, int y, int width, int height, int z, Color color) {
		super(x, y, width, height);
		ZOrder = z;
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect(x, y, width, height);
	}
}
