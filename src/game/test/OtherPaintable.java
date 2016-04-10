package game.test;

import java.awt.Color;
import java.awt.Graphics2D;

import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;

public class OtherPaintable extends BasicGameObject implements Paintable {

	private Color color;

	public OtherPaintable(int x, int y, int width, int height, int z, Color color) {
		super(x, y, width, height, z);
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect((int)x, (int)y, width, height);
	}
}
