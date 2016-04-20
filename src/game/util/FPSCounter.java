package game.util;

/**
 * <p>
 * The {@link FPSCounter} provides a static methods for measuring Frames Per
 * Second or any other measurement per second.
 * </p>
 * <p>
 * To use this class call the {@link #update(float)} method whenever the
 * measurement increases with one and pass the time since the last measurement
 * increase
 * </p>
 * 
 * @version 1.0
 * @author Julius Häger
 */
public final class FPSCounter {

	// JAVADOC: FPSCounter
	
	// TODO: Merge with UpdateCounter

	/**
	 * 
	 */
	public static float timeTot = 0;

	private static float time = 0;

	/**
	 * 
	 */
	public static long fps = 0;

	/**
	 * 
	 */
	public static long framesTot = 0;

	/**
	 * 
	 */
	public static long averageFPS = 0;

	private static long frames = 0;

	/**
	 * <p>
	 * Use this to update the counter with one measurement
	 * </p>
	 * <p>
	 * <i>Time is measured in seconds</i>
	 * </p>
	 * 
	 * @param elapsedTime
	 *            the time since last {@link #update(float)} method call
	 */
	public static void update(float elapsedTime) {
		timeTot += elapsedTime;
		time += elapsedTime;
		framesTot++;
		frames++;
		if (time > 2) {
			fps = (long) (frames / time);
			averageFPS = (long) (framesTot / timeTot);
			time = 0;
			frames = 0;
		}
	}
}
