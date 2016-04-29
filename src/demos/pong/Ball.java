package demos.pong;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import demos.pong.Pad.Side;
import demos.pong.event.PlayerScoreEvent;
import game.Game;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;

/**
 * @author Julius Häger
 *
 */
public class Ball extends Sprite implements Collidable{

	private int minDX = 150;

	private Rectangle outerBounds;

	private Sound beep;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param outerBounds
	 */
	public Ball(int x, int y, int width, int height, Rectangle outerBounds) {
		super(x, y, width, height);
		this.outerBounds = outerBounds;
		try {
			beep = IOHandler.load(new LoadRequest<Sound>("BeepSound", new File("./res/pongbeep.wav"), Sound.class, "DefaultSoundLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		if (!outerBounds.contains(bounds)) {
			if (bounds.getMinY() < outerBounds.getMinY()) {
				setY(outerBounds.y);
				setDY(-getDY());
			} else if (bounds.getMaxY() > outerBounds.getMaxY()) {
				setY(outerBounds.y + outerBounds.height - bounds.height);
				setDY(-getDY());
			}
			if (bounds.getMinX() < outerBounds.getMinX()) {
				
				Game.eventMachine.fireEvent(new PlayerScoreEvent(this, Side.RIGHT));
				
				Game.log.logMessage("Right player scored", "Pong", "Score");
				resetBall();
			} else if (bounds.getMaxX() > outerBounds.getMaxX()) {
				
				Game.eventMachine.fireEvent(new PlayerScoreEvent(this, Side.LEFT));
				
				Game.log.logMessage("Left player scored", "Pong", "Score");
				resetBall();
			}
			AudioEngine.playSound(new AudioSource(x, y, beep));
		}
	}

	/**
	 * 
	 */
	public void resetBall() {
		setDX(0);
		setDY(0);
		setX((float) (outerBounds.getMaxX() / 2 - width));
		setY((float) (outerBounds.getMaxY() / 2 - height));
		Random rand = new Random();
		setDX(rand.nextInt(200) - 100);
		setDY(rand.nextInt(200) - 100);
		if (Math.abs(getDX()) < minDX) {
			if (getDX() < 0) {
				setDX(-minDX);
			} else {
				setDX(minDX);
			}
		}
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		return;
	}
}
