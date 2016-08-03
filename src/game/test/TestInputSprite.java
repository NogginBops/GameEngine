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
		g2d.fillRect(0, 0, (int) getWidth(), (int) getHeight());
		g2d.setColor(color.darker());
		g2d.fillOval(0, 0, (int) getWidth(), (int) getHeight());
		g2d.setColor(Color.YELLOW);
		if (lastXInSpriteBounds < 0) {
			lastXInSpriteBounds = (int) 0;
		}
		if (lastYInSpriteBounds < 0) {
			lastYInSpriteBounds = (int) 0;
		}
		if (lastXInSpriteBounds > getWidth()) {
			lastXInSpriteBounds = (int) (getWidth());
		}
		if (lastYInSpriteBounds > getHeight()) {
			lastYInSpriteBounds = (int) (getHeight());
		}
		g2d.fillRect(lastXInSpriteBounds - 5, lastYInSpriteBounds - 5, 10, 10);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (shape.contains(e.getPoint())) {
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
		if (shape.contains(e.getX(), e.getY())) {
			lastXInSpriteBounds = e.getX();
			lastYInSpriteBounds = e.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (shape.contains(e.getX(), e.getY())) {
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
