package game.sound;

import java.awt.geom.Point2D;

import kuusisto.tinysound.Sound;


/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class AudioSource {
	
	//JAVADOC: AudioSource
	
	private Point2D location;
	
	private Sound sound;
	
	public AudioSource(double x, double y, Sound sound) {
		location = new Point2D.Double(x, y);
		this.sound = sound;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}
}
