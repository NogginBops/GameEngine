package game.util;

/**
 * A UpdateListener is added to a Updater to receive time independent updates.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface UpdateListener{
	
	/**
	 * Called by a Updater to update a UpdateListener.
	 * 
	 * @param timeNano this time since the last update. <br>
	 * (in nanoseconds)
	 */
	public void update(long timeNano);

}
