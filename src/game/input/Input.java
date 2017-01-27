package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import game.Game;
import game.gameObject.BasicGameObject;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
import game.util.UpdateListener;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Input extends BasicGameObject implements KeyListener, MouseInputListener, MouseWheelListener, UpdateListener{
	
	//TODO: Merge Input, MouseInputHandler and KeyInputHandler

	private MouseInputHandler mouseHandler;

	private KeyInputHandler keyHandler;
	
	//TODO: This class should be update synced and the Mouse and Key handlers should do their own thing?

	/**
	 * @param mouseHandeler
	 * @param keyHandeler
	 */
	public Input(MouseInputHandler mouseHandeler, KeyInputHandler keyHandeler) {
		super(0, 0, 0, 0, 0);
		this.mouseHandler = mouseHandeler;
		this.keyHandler = keyHandeler;
		
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, (event) -> {
			if(event.object instanceof game.input.keys.KeyListener){
				keyHandler.addListener((game.input.keys.KeyListener) event.object);
			}});
		
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, (event) -> {
			if(event.object instanceof game.input.keys.KeyListener){
				keyHandler.removeListener((game.input.keys.KeyListener) event.object);
			}});
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
		//mouseHandler.computeEnteredListeners();
	}
}
