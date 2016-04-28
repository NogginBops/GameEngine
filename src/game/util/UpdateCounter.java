package game.util;

/**
 * 
 * @author Julius Häger
 * @version 1.0
 */
public class UpdateCounter {

	// JAVADOC: UpdateCounter
	
	// TODO: Merge with FPSCounter

	/**
	 * 
	 */
	public static float timeTot = 0;

	private static float time = 0;

	/**
	 * 
	 */
	public static long ups = 0;

	/**
	 * 
	 */
	public static long updatesTot = 0;

	/**
	 * 
	 */
	public static long averageUPS = 0;

	private static long updates = 0;

	/**
	 * <p>
	 * Use this to update the counter with one measurement
	 * </p>
	 * <p>
	 * <i>Time is measured in seconds</i>
	 * </p>
	 * 
	 * @param elapsedTime
	 *            the time since the last call to this method
	 */
	public static void update(float elapsedTime) {
		timeTot += elapsedTime;
		time += elapsedTime;
		updatesTot++;
		updates++;
		
		//TODO: Use a variable for interval
		if (time > 1) {
			ups = (long) (updates / time);
			averageUPS = (long) (updatesTot / timeTot);
			time = 0;
			updates = 0;
		}
	}
}
