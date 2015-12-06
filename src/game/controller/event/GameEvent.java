package game.controller.event;

import java.lang.Comparable;

public abstract class GameEvent implements Comparable<GameEvent> {
	
	public Object origin;
	
	public GameEvent(Object origin) {
		this.origin = origin;
	}
	
	public String toString(){
		return "GameEvent[Origin=" + origin + "]";
	}
}
