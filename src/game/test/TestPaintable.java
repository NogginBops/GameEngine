package game.test;

import java.awt.Graphics2D;

import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;

public class TestPaintable extends BasicGameObject implements Paintable {

	public TestPaintable(int x, int y, int width, int height) {
		super(x, y, width, height, 0);

	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.fillRect((int)x, (int)y, width, height);
	}
}
