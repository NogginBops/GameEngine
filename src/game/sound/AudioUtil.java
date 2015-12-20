package game.sound;

import java.awt.geom.Point2D;

/**
 * @author Julius Häger
 * @version 1.0
 */
public final class AudioUtil {

	// JAVADOC: AudioUtil

	/**
	 * @param source
	 * @param listener
	 * @return
	 */
	public static double distance(Point2D source, Point2D listener) {
		return Math.sqrt(distanceSqrd(source, listener));
	}

	/**
	 * @param source
	 * @param listener
	 * @return
	 */
	public static double distanceSqrd(Point2D source, Point2D listener) {
		return (source.getX() - listener.getX()) * (source.getX() - listener.getX())
				+ (source.getY() - listener.getY()) * (source.getY() - listener.getY());
	}

	/**
	 * @param source
	 * @param listener
	 * @return
	 */
	public static Point2D getVector(Point2D source, Point2D listener) {
		return new Point2D.Double(source.getX() - listener.getX(), source.getY() - listener.getY());
	}
}
