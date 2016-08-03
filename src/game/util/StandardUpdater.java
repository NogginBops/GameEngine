package game.util;

import game.Game;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.gameObject.handler.event.GameObjectEvent;

/**
 * @author Julius Häger
 *
 */
public class StandardUpdater extends Updater implements EventListener{
	
	//NOTE: Is this event needed? Should this behavior just be a part of the Updater class

	/**
	 * 
	 */
	public StandardUpdater() {
		super();
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, this);
	}
	
	@Override
	public <T extends GameEvent<?>> void eventFired(T event) {
		if(event instanceof GameObjectEvent){
			GameObjectEvent goEvent = (GameObjectEvent) event;
			if(goEvent.object instanceof UpdateListener){
				switch (event.command) {
				case "Created":
					listeners.add((UpdateListener) goEvent.object);
					break;
				case "Destroyed":
					listeners.remove((UpdateListener) goEvent.object);
					break;
				default:
					break;
				}
			}
		}
	}
}
