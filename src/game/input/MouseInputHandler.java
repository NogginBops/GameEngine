package game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.MouseInputListener;

import game.Game;
import game.GameSystem;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.gameObject.graphics.Camera;
import game.gameObject.handler.event.GameObjectEvent;
import game.input.mouse.MouseListener;
import game.util.InverseGameObjectComparator;

/**
 * @author Julius Häger
 *
 */
public class MouseInputHandler extends GameSystem implements MouseInputListener, MouseWheelListener, EventListener {
	
	//TODO: Have separate lists for the entered listeners and the listeners that always receive updates

	//FIXME: Add support for multiple cameras and 
	
	private Camera camera;

	private CopyOnWriteArrayList<MouseListener> listeners = new CopyOnWriteArrayList<>();

	private CopyOnWriteArrayList<MouseListener> enteredListeners = new CopyOnWriteArrayList<>();
	
	private MouseEvent lastEvent;
	
	private InverseGameObjectComparator invComparator = new InverseGameObjectComparator();
	
	/**
	 * @param camera
	 */
	public MouseInputHandler(Camera camera) {
		super("MouseInputHandler");
		
		this.camera = camera;
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, this);
		
		Game.screen.addDebugText(() -> {
			return new String[]{
					"Mouse X: " + (lastEvent == null ? 0 : lastEvent.getX()),
					"Mouse Y " + (lastEvent == null ? 0 : lastEvent.getY())
			};
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseClicked(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		processMouseEvent(e);
		
		/*for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseEntered(e);
		}*/
	}

	@Override
	public void mouseExited(MouseEvent e) {
		processMouseEvent(e);
		
		/*for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseExited(e);
		}*/
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseMoved(e);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		processMouseEvent(e);
		
		for (MouseListener mouseListener : enteredListeners) {
			mouseListener.mouseWheelMoved(e);
		}
	}
	
	private void processMouseEvent(MouseEvent e){
		
		/*e.translatePoint((int) camera.getX() - Game.screen.getInsets().right,
				(int) camera.getY() - Game.screen.getInsets().top);
		*/
		
		e.translatePoint((int) camera.getX(),
				(int) camera.getY());
		
		float rotation = camera.getTransform().getRotationRad();
		
		e.translatePoint(
				(int)((((e.getX()) * Math.cos(rotation)) - ((e.getY()) * Math.sin(rotation))) - e.getX()),
				(int)((((e.getY()) * Math.cos(rotation)) + ((e.getX()) * Math.sin(rotation))) - e.getY()));
		
		e.translatePoint((int) -camera.getX() - Game.screen.getInsets().right,
				(int) -camera.getY() - Game.screen.getInsets().top);
		
		lastEvent = e;
		
		computeEnteredListeners();
	}
	
	private void computeEnteredListeners(){
		//System.out.println("Size: " + listeners.size());
		//System.out.println("Size enterd: " + enteredListeners.size());
		if(lastEvent != null){
			for (MouseListener mouseListener : listeners) {
				if(mouseListener.isActive()){
					if(mouseListener.souldReceiveMouseInput() || mouseListener.getBounds().contains(lastEvent.getPoint())){
						if(enteredListeners.contains(mouseListener) == false){
							enteredListeners.add(mouseListener);
							enteredListeners.sort(invComparator);
							mouseListener.mouseEntered(lastEvent);
						}
					}else{
						if(enteredListeners.contains(mouseListener) == true){
							enteredListeners.remove(mouseListener);
							mouseListener.mouseExited(lastEvent);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void earlyUpdate(float deltaTime) {
		computeEnteredListeners();
	}

	@Override
	public void lateUpdate(float deltaTime) {
		
	}

	//TODO: Add individual handlers for events instead of this where you have to figure out what event you are receiving
	@Override
	public <T extends GameEvent<?>> void eventFired(T event) {
		if (event instanceof GameObjectEvent) {
			if(((GameObjectEvent)event).object instanceof MouseListener){
				switch (event.command) {
				case "Created":
					listeners.add((MouseListener)((GameObjectEvent)event).object);
					break;
				case "Destroyed":
					listeners.remove(((GameObjectEvent)event).object);
					enteredListeners.remove(((GameObjectEvent)event).object);
					break;
				default:
					break;
				}
			}
		}
	}
}
