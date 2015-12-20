package game.sound;

import java.awt.geom.Point2D;

import kuusisto.tinysound.Sound;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class AudioSource {

	// JAVADOC: AudioSource

	private Point2D location;

	private Sound sound;

	/**
	 * @param x
	 * @param y
	 * @param sound
	 */
	public AudioSource(double x, double y, Sound sound) {
		location = new Point2D.Double(x, y);
		this.sound = sound;
	}

	/**
	 * @return
	 */
	public Sound getSound() {
		return sound;
	}

	/**
	 * @param sound
	 */
	public void setSound(Sound sound) {
		this.sound = sound;
	}

	/**
	 * @return
	 */
	public Point2D getLocation() {
		return location;
	}

	/**
	 * @param location
	 */
	public void setLocation(Point2D location) {
		this.location = location;
	}
}
