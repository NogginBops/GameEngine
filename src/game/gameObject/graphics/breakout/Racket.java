package game.gameObject.graphics.breakout;

import game.gameObject.graphics.Sprite;
import game.input.keys.KeyListener;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * @author Julius Häger
 *
 */
public class Racket extends Sprite implements KeyListener {

	private boolean moveLeft;
	private boolean moveRight;

	/**
	 * 
	 */
	public int movementSpeed = 250;

	private Rectangle outerBounds;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param outerBounds
	 */
	public Racket(int x, int y, int width, int height, Rectangle outerBounds) {
		super(x, y, width, height);
		this.outerBounds = outerBounds;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
		// g2d.fill(bounds);
	}

	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		if (!outerBounds.contains(bounds)) {
			if (bounds.x + bounds.width > outerBounds.x + outerBounds.width) {
				setX((int) outerBounds.getMaxX() - bounds.width);
			} else if (bounds.x < outerBounds.x) {
				setX(outerBounds.x);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			moveLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			moveRight = true;
			break;
		default:
			break;
		}
		updateMovement();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			moveLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			moveRight = false;
			break;
		default:
			break;
		}
		updateMovement();
	}

	private void updateMovement() {
		int dx = 0;
		dx += moveLeft ? -movementSpeed : 0;
		dx += moveRight ? movementSpeed : 0;
		setDX(dx);
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}
}
