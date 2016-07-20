package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import game.Game;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.gameObject.BasicGameObject;
import game.gameObject.handler.event.GameObjectEvent;
import game.util.UpdateListener;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Input extends BasicGameObject implements KeyListener, MouseInputListener, MouseWheelListener, EventListener, UpdateListener{
	
	//TODO: Merge Input, MouseInputHandler and KeyInputHandler

	private MouseInputHandler mouseHandler;

	private KeyInputHandler keyHandler;
	
	//TODO: Key-binding system
	
	//TODO: This class should be update synced and the Mouse and Key handlers should do their own thing?

	/**
	 * @param mouseHandeler
	 * @param keyHandeler
	 */
	public Input(MouseInputHandler mouseHandeler, KeyInputHandler keyHandeler) {
		super(0, 0, 0, 0, 0);
		this.mouseHandler = mouseHandeler;
		this.keyHandler = keyHandeler;
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, this);
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

	@Override
	public <T extends GameEvent<?>> void eventFired(T event) {
		if(event instanceof GameObjectEvent){
			GameObjectEvent goEvent = (GameObjectEvent) event;
			
			switch (goEvent.command) {
			case "Created":
				if(goEvent.object instanceof game.input.keys.KeyListener){
					keyHandler.addListener((game.input.keys.KeyListener) goEvent.object);
				}
				break;
			case "Destroyed":
				if(goEvent.object instanceof game.input.keys.KeyListener){
					keyHandler.removeListener((game.input.keys.KeyListener) goEvent.object);
				}
				break;
			default:
				break;
			}
		}
	}
}
