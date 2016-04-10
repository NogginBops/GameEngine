package game.test;

import java.awt.Color;
import java.awt.Graphics2D;

import game.gameObject.graphics.Sprite;

/**
 * @author Julius Häger
 *
 */
public class TestSprite extends Sprite {

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public TestSprite(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.CYAN);
		g2d.fillRect((int) x, (int) y, (int) width, (int) height);
	}
}
