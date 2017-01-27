package demos.pong.event;

import demos.pong.Ball;
import demos.pong.Pad.Side;
import game.controller.event.GameEvent;

/**
 * @author Julius Häger
 *
 */
public class PlayerScoreEvent extends GameEvent {

	/**
	 * 
	 */
	public final Side side;
	
	/**
	 * @param origin
	 * @param side 
	 * @param command
	 */
	public PlayerScoreEvent(Ball origin, Side side) {
		super(origin);
		this.side = side;
	}

}
