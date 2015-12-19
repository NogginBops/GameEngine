package demos.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import game.Game;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.IO.load.LoadResult;
import game.IO.save.SaveRequest;
import game.gameObject.graphics.Sprite;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Score extends Sprite implements KeyListener {

	/**
	 * 
	 */
	public static int left = 0;

	/**
	 * 
	 */
	public static int right = 0;

	private Rectangle devider;

	private int deviderWidth = 4;

	@SuppressWarnings("unused")
	private BufferedImage img;

	/**
	 * @param bounds
	 */
	public Score(Rectangle bounds) {
		super(bounds);
		devider = new Rectangle((int) (bounds.getWidth() / 2 - deviderWidth / 2), bounds.y, deviderWidth,
				bounds.height);
		zOrder = 0;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fill(bounds);
		// g2d.drawImage(img, (int)x, (int)y, null);
		g2d.setColor(Color.WHITE);
		g2d.fill(devider);
		g2d.drawString("" + left, devider.x - 50, 40);
		g2d.drawString("" + right, devider.x + 50, 40);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			if (!Game.isPaused()) {
				Game.pause();
			} else {
				Game.resume();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			Game.stop();
			break;
		case KeyEvent.VK_B:
			if (IOHandler.save(
					new ArrayList<SaveRequest<?>>(Arrays.asList(new SaveRequest<String>("P1: " + left + " P2: " + right,
							String.class, new File("./res/ayyy/Score.txt"), "Default String Saver"))))) {
				System.out.println("Save sucsessfull");
			} else {
				System.out.println("Save failed");
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}
}
