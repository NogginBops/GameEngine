package game;

import java.io.File;

import game.gameObject.graphics.Camera;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * @author Julius Häger
 *
 */
public class AudioTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new AudioTest();
	}
	
	/**
	 * 
	 */
	public AudioTest() {
		
		Camera cam = new Camera(null, -1, -1, 2, 2);
		
		AudioEngine.setAudioListener(cam);
		
		Sound sound = TinySound.loadSound(new File("./res/robot.mp3"));
		
		AudioSource test = new AudioSource(0f, 0f, sound);
		
		AudioEngine.playSound(test);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
