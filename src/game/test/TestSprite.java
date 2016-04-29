package game.test;

import java.awt.Color;

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
		super(x, y, width, height, Color.CYAN);
	}
}
