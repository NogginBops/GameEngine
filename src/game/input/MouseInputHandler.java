package game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.MouseInputListener;

import game.Game;
import game.GameSystem;
import game.debug.DebugOutputProvider;
import game.gameObject.graphics.Camera;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
import game.input.mouse.MouseListener;

/**
 * @author Julius Häger
 *
 */
public class MouseInputHandler extends GameSystem implements MouseInputListener, MouseWheelListener, DebugOutputProvider {
	
	//JAVADOC: MouseInputHandler
	
	//TODO: Have separate lists for the entered listeners and the listeners that always receive updates

	//FIXME: Add support for multiple cameras
	
	private Camera camera;

	private CopyOnWriteArrayList<MouseListener> listeners = new CopyOnWriteArrayList<>();

	private CopyOnWriteArrayList<MouseListener> enteredListeners = new CopyOnWriteArrayList<>();
	
	private MouseEvent lastEvent;
	
	/**
	 * @param camera
	 */
	public MouseInputHandler(Camera camera) {
		super("Mouse Input Handler");
		
		this.camera = camera;
		
		//FIXME: Add event for MouseListeners!
		// or we could go back to an older system where the MouseListener needed to register to the handler.
		
		// The best way to implement this would be with a GameSystemListenerCreatedEvent<GameSystemListener> or something like that
		// that can supply the listener with exactly what it's looking for. This would mean that you would have to use something like 
		
		// You could also just refactor this so that anyone can subscribe to the mouse input and other input with method references and lambdas.
		// MouseInputHandler.onClick(Runnable)
		// MouseInputHandler.onClick(Predicate<MouseEvent>, Runnable)
		// or something similar*9/
		
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, this::mouseListenerCreated);
		
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, this::mouseListenerDestroyed);
				
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
		processMouseEvent(e);
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
		
		e.translatePoint((int) -camera.getX(),
				(int) -camera.getY());
		
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
		if(lastEvent != null){
			for (MouseListener mouseListener : listeners) {
				if(mouseListener.isActive()){
					if(mouseListener.souldReceiveMouseInput() || mouseListener.getBounds().contains(lastEvent.getPoint())){
						if(enteredListeners.contains(mouseListener) == false){
							enteredListeners.add(mouseListener);
							enteredListeners.sort((m, m2) -> m2.getZOrder() - m.getZOrder());
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
	
	/**
	 * @param listener
	 */
	public void addMouseListener(MouseListener listener){
		listeners.add(listener);
	}
	
	/**
	 * @param listener
	 */
	public void removeMouseListener(MouseListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * @param event
	 */
	private void mouseListenerCreated(GameObjectCreatedEvent event){
		if (event.object instanceof MouseListener) {
			addMouseListener((MouseListener)event.object);
		}
	}
	
	/**
	 * @param event
	 */
	private void mouseListenerDestroyed(GameObjectDestroyedEvent event){
		if (event.object instanceof MouseListener) {
			removeMouseListener((MouseListener)event.object);
		}
	}

	@Override
	public String[] getDebugValues() {
		return new String[]{ 
				"<b>Listeners: </b>" + listeners.size(),
				"<b>Entered Listeners: </b>" + enteredListeners.size(),
				"<b>Mouse X: </b>" + (lastEvent == null ? null : lastEvent.getX()),
				"<b>Mouse Y: </b>" + (lastEvent == null ? null : lastEvent.getY()),
		};
	}
}
