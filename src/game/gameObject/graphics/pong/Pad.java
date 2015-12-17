package game.gameObject.graphics.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;

/**
 * @author Julius Häger
 *
 */
public class Pad extends Sprite implements KeyListener, Collidable {

	/**
	 * @author Julius Häger
	 *
	 */
	public enum Side {
		/**
		 * 
		 */
		LEFT,
		/**
		 * 
		 */
		RIGHT
	}

	private Side side;

	private Rectangle outerBounds;

	private int upKeyCode;
	private int downKeyCode;

	private int padMovmentSpeed = 200;

	private boolean moveUp;
	private boolean moveDown;

	private int maxInclenationChange = 50;

	private int speedChange = 0;

	private Sound beep;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param upKeyCode
	 * @param downKeyCode
	 * @param outerBounds
	 * @param side
	 */
	public Pad(int x, int y, int width, int height, int upKeyCode, int downKeyCode, Rectangle outerBounds, Side side) {
		super(x, y, width, height);
		this.upKeyCode = upKeyCode;
		this.downKeyCode = downKeyCode;
		this.outerBounds = outerBounds;
		this.side = side;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fill(bounds);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == upKeyCode) {
			moveUp = true;
		} else if (e.getKeyCode() == downKeyCode) {
			moveDown = true;
		}
		updateMovement();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == upKeyCode) {
			moveUp = false;
		} else if (e.getKeyCode() == downKeyCode) {
			moveDown = false;
		}
		updateMovement();
	}

	private void updateMovement() {
		int dy = 0;
		dy += moveUp ? -padMovmentSpeed : 0;
		dy += moveDown ? padMovmentSpeed : 0;
		setDY(dy);
	}

	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		if (!outerBounds.contains(bounds)) {
			if (bounds.y + bounds.height > outerBounds.y + outerBounds.height) {
				setY((int) outerBounds.getMaxY() - bounds.height);
			} else if (bounds.y < outerBounds.y) {
				setY(outerBounds.y);
			}
		}
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		Rectangle2D ballBounds = collisionObject.getBounds();
		float inclenation = (float) (ballBounds.getY() - bounds.y) / bounds.height;
		inclenation = inclenation < 0 ? 0 : inclenation;
		float newDX = Math.abs(collisionObject.getDX()) + speedChange;
		float newDY = maxInclenationChange * inclenation * 2 - maxInclenationChange;
		if (side == Side.LEFT) {
			collisionObject.setDX(newDX);
		} else if (side == Side.RIGHT) {
			collisionObject.setDX(-newDX);
		}
		collisionObject.setDY(collisionObject.getDY() + newDY);
		AudioEngine.playSound(new AudioSource(x, y, beep));
	}

	@Override
	public int getMass() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void willCollide(Collidable collisionObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void willNoLongerCollide(Collidable collidedObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void noLongerColliding(Collidable collidedObject) {
		// TODO Auto-generated method stub

	}
}
