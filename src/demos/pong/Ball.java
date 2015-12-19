package demos.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import game.gameObject.graphics.Sprite;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;

/**
 * @author Julius Häger
 *
 */
public class Ball extends Sprite {

	private int minDX = 150;

	private Rectangle outerBounds;

	private Sound beep;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param outerBounds
	 */
	public Ball(int x, int y, int width, int height, Rectangle outerBounds) {
		super(x, y, width, height);
		this.outerBounds = outerBounds;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fill(bounds);
	}

	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		if (!outerBounds.contains(bounds)) {
			if (bounds.getMinY() < outerBounds.getMinY()) {
				setY(outerBounds.y);
				setDY(-getDY());
			} else if (bounds.getMaxY() > outerBounds.getMaxY()) {
				setY(outerBounds.y + outerBounds.height - bounds.height);
				setDY(-getDY());
			}
			if (bounds.getMinX() < outerBounds.getMinX()) {
				Score.right++;
				resetBall();
			} else if (bounds.getMaxX() > outerBounds.getMaxX()) {
				Score.left++;
				resetBall();
			}
			AudioEngine.playSound(new AudioSource(x, y, beep));
		}
	}

	/**
	 * 
	 */
	public void resetBall() {
		setDX(0);
		setDY(0);
		setX((float) (outerBounds.getMaxX() / 2 - width));
		setY((float) (outerBounds.getMaxY() / 2 - height));
		Random rand = new Random();
		setDX(rand.nextInt(200) - 100);
		setDY(rand.nextInt(200) - 100);
		if (Math.abs(getDX()) < minDX) {
			if (getDX() < 0) {
				setDX(-minDX);
			} else {
				setDX(minDX);
			}
		}
	}
}
