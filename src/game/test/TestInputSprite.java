package game.test;

import game.gameObject.graphics.Sprite;
import game.input.keys.KeyListener;
import game.input.mouse.MouseListener;
import game.util.math.ColorUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author Julius Häger
 *
 */
public class TestInputSprite extends Sprite implements MouseListener, KeyListener {

	private Color color;

	private Color secColor;

	private boolean absorb;

	private int lastXInSpriteBounds, lastYInSpriteBounds;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param z
	 * @param absorb
	 */
	public TestInputSprite(int x, int y, int width, int height, int z, boolean absorb) {
		super(x, y, width, height);
		zOrder = z;
		this.absorb = absorb;
		color = ColorUtils.createTransparent(Color.GREEN, 150);
		secColor = ColorUtils.createTransparent(Color.YELLOW, 150);
		lastXInSpriteBounds = x;
		lastYInSpriteBounds = y;
	}

	@Override
	public void paint(Graphics2D g2d) {
		
		//This is custom paint behavior that should not inherit Sprites paint behavior.
		
		g2d.setColor(color);
		g2d.fillRect((int) x, (int) y, (int) width, (int) height);
		g2d.setColor(color.darker());
		g2d.fillOval((int) x, (int) y, (int) width, (int) height);
		g2d.setColor(Color.YELLOW);
		if (lastXInSpriteBounds < x) {
			lastXInSpriteBounds = (int) x;
		}
		if (lastYInSpriteBounds < y) {
			lastYInSpriteBounds = (int) y;
		}
		if (lastXInSpriteBounds > x + width) {
			lastXInSpriteBounds = (int) (x + width);
		}
		if (lastYInSpriteBounds > y + height) {
			lastYInSpriteBounds = (int) (y + height);
		}
		g2d.fillRect(lastXInSpriteBounds - 5, lastYInSpriteBounds - 5, 10, 10);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (bounds.contains(e.getPoint())) {
			Color tempColor = color;
			color = secColor;
			secColor = tempColor;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (bounds.contains(e.getX(), e.getY())) {
			lastXInSpriteBounds = e.getX();
			lastYInSpriteBounds = e.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (bounds.contains(e.getX(), e.getY())) {
			lastXInSpriteBounds = e.getX();
			lastYInSpriteBounds = e.getY();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public boolean absorb() {
		return absorb;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return false;
	}
}
