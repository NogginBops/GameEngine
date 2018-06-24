package game.sound;

import java.awt.geom.Point2D;

import kuusisto.tinysound.Sound;

/**
 * 
 * @version 1.0
 * @author Julius H�ger
 */
public class AudioSource {

	// JAVADOC: AudioSource

	private Point2D location;

	private Sound sound;
	
	private float volume = 1;

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
	 * @param x
	 * @param y
	 * @param sound
	 * @param volume 
	 */
	public AudioSource(double x, double y, Sound sound, float volume) {
		location = new Point2D.Double(x, y);
		this.sound = sound;
		this.volume = volume;
	}
	
	/**
	 * @return
	 */
	public float getVolume(){
		return volume;
	}
	
	/**
	 * @param volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
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
