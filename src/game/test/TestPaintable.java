package game.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;

/**
 * @author Julius Häger
 *
 */
public class TestPaintable extends BasicGameObject implements Paintable {

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public TestPaintable(int x, int y, int width, int height) {
		super(x, y, width, height, 0);

	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.fillRect(0, 0, (int)getWidth(), (int)getHeight());
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
