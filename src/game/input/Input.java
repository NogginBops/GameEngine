package game.input;

import game.Game;
import game.gameObject.BasicGameObject;
import game.util.UpdateListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Input extends BasicGameObject implements KeyListener, MouseInputListener, MouseWheelListener, UpdateListener {
	
	//TODO: Merge Input, MouseInputHandler and KeyInputHandler

	private MouseInputHandler mouseHandler;

	private KeyInputHandler keyHandler;
	
	//TODO: Key-binding system

	/**
	 * @param mouseHandeler
	 * @param keyHandeler
	 */
	public Input(MouseInputHandler mouseHandeler, KeyInputHandler keyHandeler) {
		super(0, 0, 0, 0, 0);
		this.mouseHandler = mouseHandeler;
		this.keyHandler = keyHandeler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseHandler.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseHandler.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseHandler.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseHandler.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseHandler.mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseHandler.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseHandler.mouseMoved(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseHandler.mouseWheelMoved(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keyHandler.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyHandler.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyHandler.keyReleased(e);
	}

	@Override
	public void update(float deltaTime) {
		mouseHandler.computeEnteredListeners();
		
		if(Game.gameObjectHandler.shouldUpdateObjects()){
			keyHandler.updateListeners();
		}
	}
}
