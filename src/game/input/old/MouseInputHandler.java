package game.input.old;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.gameObject.graphics.Camera;
import game.input.mouse.MouseListener;
import game.util.InverseGameObjectComparator;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class MouseInputHandler {

	// JAVADOC: MouseInputHandler
	
	//FIXME: Respond to multiple cameras
	//NOTE: Its better to fix this when multiple screens are supported as that will also effect input.

	private CopyOnWriteArrayList<MouseListener> listeners = new CopyOnWriteArrayList<>();

	private CopyOnWriteArrayList<MouseListener> enteredListeners;

	private Camera camera;

	private InverseGameObjectComparator invComparator;

	private MouseEvent lastEvent;

	/**
	 * 
	 * 
	 * @param gameObjectHandeler
	 * @param camera
	 */
	public MouseInputHandler(Camera camera) {
		this.camera = camera;
		enteredListeners = new CopyOnWriteArrayList<MouseListener>();
		invComparator = new InverseGameObjectComparator();
	}

	/**
	 * 
	 * 
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		mouseMoved(e);
		for (MouseListener listener : enteredListeners) {
			listener.mouseClicked(e);
			if (listener.absorb()) {
				break;
			}
		}
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		mouseMoved(e);
		for (MouseListener listener : enteredListeners) {
			listener.mousePressed(e);
			if (listener.absorb()) {
				break;
			}
		}
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		mouseMoved(e);
		for (MouseListener listener : enteredListeners) {
			listener.mouseReleased(e);
			if (listener.absorb()) {
				break;
			}
		}
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
		e.translatePoint((int) camera.getX() - Game.screen.getInsets().right,
				(int) camera.getY() - Game.screen.getInsets().top);
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		e.translatePoint((int) camera.getX() - Game.screen.getInsets().right,
				(int) camera.getY() - Game.screen.getInsets().top);
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e) {
		e.translatePoint((int) camera.getX() - Game.screen.getInsets().right,
				(int) camera.getY() - Game.screen.getInsets().top);
		
		for (MouseListener listener : listeners) {
			listener.mouseDragged(e);
		}
		
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseMoved(MouseEvent e) {
		e.translatePoint((int) camera.getX() - Game.screen.getInsets().right,
				(int) camera.getY() - Game.screen.getInsets().top);
		
		listeners.sort(invComparator);
		for (MouseListener listener : listeners) {
			if(listener.isActive()){
				if (listener.getBounds().contains(e.getX(), e.getY()) || listener.souldReceiveMouseInput()) {
					listener.mouseMoved(e);
					if (!enteredListeners.contains(listener)) {
						listener.mouseEntered(e);
						enteredListeners.add(listener);
						enteredListeners.sort(invComparator);
					}
				} else {
					if (enteredListeners.contains(listener)) {
						listener.mouseExited(e);
						enteredListeners.remove(listener);
					}
				}
			}
		}
		
		lastEvent = e;
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseMoved(e);
		for (MouseListener listener : enteredListeners) {
			listener.mouseWheelMoved(e);
			if (listener.absorb()) {
				break;
			}
		}
		lastEvent = e;
	}

	/**
	 * 
	 */
	public void computeEnteredListeners() {
		if (lastEvent != null) {
			listeners.sort(invComparator);
			for (MouseListener listener : listeners) {
				if(listener.isActive()){
					if (listener.getBounds().contains(lastEvent.getX(), lastEvent.getY())
							|| listener.souldReceiveMouseInput()) {
						if (!enteredListeners.contains(listener)) {
							listener.mouseEntered(lastEvent);
							enteredListeners.add(listener);
							enteredListeners.sort(invComparator);
						}
					} else {
						if (enteredListeners.contains(listener)) {
							listener.mouseExited(lastEvent);
							enteredListeners.remove(listener);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param listener
	 */
	public void addListener(MouseListener listener){
		listeners.add(listener);
		computeEnteredListeners();
	}
	
	/**
	 * @param listener
	 */
	public void removeListener(MouseListener listener){
		listeners.remove(listener);
		computeEnteredListeners();
	}
}
