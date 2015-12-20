package demos.breakout;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import game.Game;
import game.gameObject.GameObject;
import game.input.keys.KeyListener;
import game.util.UpdateListener;

/**
 * @author Julius Häger
 *
 */
public class GM implements GameObject, UpdateListener, KeyListener {

	@Override
	public void update(long timeMillis) {

	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void updateBounds() {

	}

	@Override
	public int getZOrder() {
		return 0;
	}

	@Override
	public int compareTo(GameObject object) {
		return 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Game.stop();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}

}
