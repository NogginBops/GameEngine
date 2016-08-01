package game.sound;

import java.awt.geom.Point2D;

import game.gameObject.GameObject;
import game.util.math.MathUtils;
import kuusisto.tinysound.TinySound;

/**
 * 
 * @author Julius Häger
 * @version 1.0
 */
public class AudioEngine {

	// JAVADOC: AudioEngine
	
	//TODO: Either replace TinySound with a custom written system or use another library
	
	private static GameObject listener;

	private static Point2D listenerLocation;

	private static double lowerThreshhold = 1;
	
	private static double lowerPanThreshhold = 0.1;
	
	private static double masterVolume = 1;

	/**
	 * @param listener
	 */
	public static void init(GameObject listener) {
		AudioEngine.listener = listener;
		TinySound.init();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TinySound.shutdown();
	}
	/**
	 * @param audioListener
	 */
	public static void setAudioListener(GameObject audioListener) {
		listener = audioListener;
	}
	
	/**
	 * Sets the masterVolume. The value is clamped between 0 and 1.
	 * @param vol
	 */
	public static void setMasterVolume(double vol){
		masterVolume = MathUtils.clamp01(vol);
		TinySound.setGlobalVolume(masterVolume);
	}

	/**
	 * @param source
	 */
	public static void playSound(AudioSource source) {
		if(!TinySound.isInitialized()){
			return;
		}
		
		listenerLocation = new Point2D.Double(listener.getBounds().getCenterX(),
				listener.getBounds().getCenterY());
		Point2D vector = AudioUtil.getVector(source.getLocation(), listenerLocation);
		Double dist = AudioUtil.distance(source.getLocation(), listenerLocation);

		double volume = Math.log10(dist) < lowerThreshhold ? 1 / lowerThreshhold : 1 / Math.log10(dist);

		double pan = dist < lowerPanThreshhold ? 0 : vector.getX() / dist;

		source.getSound().play(volume * source.getVolume(), pan);
		
		// System.out.println("Volume: " + volume + " Pan: " + pan + " Distance:
		// " + dist + " Distance log: " + Math.log10(dist) + " Vector: " +
		// vector);
	}
	
	//TODO: Add music support
}
