package demos.pong;

import java.awt.geom.Rectangle2D;
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
public class Ball extends Sprite implements Collidable {

	private int minDX = 150;

	private Rectangle2D outershape;

	private Sound beep;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param outershape
	 */
	public Ball(float x, float y, float width, float height, Rectangle2D outershape) {
		super(x, y, width, height);
		this.outershape = outershape;
		
		try {
			beep = IOHandler.load(new LoadRequest<Sound>("BeepSound", new File("./res/pongbeep.wav"), Sound.class, "DefaultSoundLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (!outershape.contains(getBounds())) {
			if (getBounds().getMinY() < outershape.getMinY()) {
				setY((float)outershape.getY());
				setDY(-getDY());
			} else if (getBounds().getMaxY() > outershape.getMaxY()) {
				setY((float)outershape.getY() + (float)outershape.getHeight() - (float)getBounds().getHeight());
				setDY(-getDY());
			}
			if (getBounds().getMinX() < outershape.getMinX()) {
				
				Game.eventMachine.fireEvent(new PlayerScoreEvent(this, Side.RIGHT));
				
				Game.log.logMessage("Right player scored", "Pong", "Score");
				resetBall();
			} else if (getBounds().getMaxX() > outershape.getMaxX()) {
				
				Game.eventMachine.fireEvent(new PlayerScoreEvent(this, Side.LEFT));
				
				Game.log.logMessage("Left player scored", "Pong", "Score");
				resetBall();
			}
			AudioEngine.playSound(new AudioSource(transform.getX(), transform.getY(), beep));
		}
	}

	/**
	 * 
	 */
	public void resetBall() {
		setDX(0);
		setDY(0);
		setX((float) (outershape.getMaxX() / 2 - getWidth()));
		setY((float) (outershape.getMaxY() / 2 - getHeight()));
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
		
	}
}
