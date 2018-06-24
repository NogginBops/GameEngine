package game.util;

import game.gameObject.GameObject;

/**
 * A UpdateListener is added to a Updater to receive time independent updates.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface UpdateListener extends GameObject {

	/**
	 * Called by a Updater to update a UpdateListener.
	 * 
	 * @param deltaTime
	 *            this time since the last update. <br>
	 *            (in seconds)
	 */
	public void update(float deltaTime);

}
