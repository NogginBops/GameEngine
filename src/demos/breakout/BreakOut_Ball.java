package demos.breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Game;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;
import game.util.IDHandler;

/**
 * @author Julius Häger
 *
 */
public class BreakOut_Ball extends Sprite implements KeyListener, Collidable {

	boolean isOnRacket = true;

	private int startVertivcalMovement = 200;

	private int maxInclenationChange = 50;

	private Rectangle outerBounds;

	private Racket racket;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param outerBounds
	 * @param racket
	 */
	public BreakOut_Ball(int x, int y, int width, int height, Rectangle outerBounds, Racket racket) {
		super(x, y, width, height);
		this.outerBounds = outerBounds;
		this.racket = racket;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillOval((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		if (isOnRacket) {
			setX(racket.getX() + racket.getBounds().width / 2 - width / 2);
			setY(racket.getY() - height);
		}
		if (!outerBounds.contains(bounds)) {
			if (bounds.getMinX() < outerBounds.getMinX()) {
				setX(outerBounds.x);
				setDX(-getDX());
			} else if (bounds.getMaxX() > outerBounds.getMaxX()) {
				setX(outerBounds.x + outerBounds.width - bounds.width);
				setDX(-getDX());
			} else if (bounds.getMinY() < outerBounds.getMinY()) {
				setY(outerBounds.y);
				setDY(-getDY());
			} else if (bounds.getMaxY() > outerBounds.getMaxY()) {
				isOnRacket = true;
				// Loose a life
			}
		} else {
			if (bounds.intersects(racket.getBounds())) {
				if (bounds.getMaxX() < racket.getX()) {
					setDX(getDX() + racket.getDX());
					setDY(-getDY());
				} else if (bounds.getMinX() > racket.getBounds().getMaxX()) {
					setDX(-getDX() + racket.getDX());
					setDY(-getDY());
				} else {
					setY(racket.getBounds().y - height);
					setDY(-getDY());
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (isOnRacket) {
				Random rand = new Random();
				setDY(-startVertivcalMovement);
				setDX(rand.nextInt(100) - 50);
				isOnRacket = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		IDHandler handler = Game.getCurrentIDHandler();
		if (handler.getObject("Racket") == collisionObject) {
			Rectangle racketBounds = collisionObject.getBounds();
			float inclenation = (float) (bounds.x - racketBounds.x) / racketBounds.width;
			float newDX = maxInclenationChange * inclenation * 2 - maxInclenationChange;
			setY(collisionObject.getBounds().y - height);
			setDY(-Math.abs(getDY()));
			setDX(newDX);
		}
	}
}
