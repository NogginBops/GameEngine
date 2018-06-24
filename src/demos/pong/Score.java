package demos.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import demos.pong.Pad.Side;
import demos.pong.event.PlayerScoreEvent;
import game.Game;
import game.IO.IOHandler;
import game.IO.save.SaveRequest;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.image.UIRect;
import game.UI.elements.text.UILabel;
import game.controller.event.EventListener;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Score extends BasicUIContainer implements KeyListener {

	private int left = 0;

	private int right = 0;

	private Rectangle2D deviderRect;

	private int deviderWidth = 4;
	
	private UILabel player1Score, player2Score;
	
	private UIRect devider;

	@SuppressWarnings("unused")
	private BufferedImage img;

	/**
	 * @param area
	 */
	public Score(Rectangle2D area) {
		super(area);
		
		deviderRect = new Rectangle2D.Float(((float)area.getWidth() / 2 - deviderWidth / 2), (float)area.getY(), deviderWidth,
				(float)area.getHeight());
		
		devider = new UIRect(deviderRect, Color.WHITE);
		
		setZOrder(0);
		
		player1Score = new UILabel("Player 1: 0");
		player1Score.setPosition(devider.getTransform().getX() - 55, 20);
		player2Score = new UILabel("Player 2: 0");
		player2Score.setPosition(devider.getTransform().getX() + 50, 20);
		
		addChild(player1Score);
		addChild(player2Score);
		addChild(devider);
		
		Game.eventMachine.addEventListener(PlayerScoreEvent.class, new EventListener<PlayerScoreEvent>() {
			@Override
			public void eventFired(PlayerScoreEvent event) {
				if(event.side == Side.RIGHT){
					right++;
				}else{
					left++;
				}
			}
		});
	}

	@Override
	public void paint(Graphics2D g2d) {
		//Not elegant but will work for now
		player1Score.setText("" + left);
		player2Score.setText("" + right);
		super.paint(g2d);
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
			if (IOHandler.save(new SaveRequest<String>("P1: " + left + " P2: " + right,
							String.class, new File("./res/Score.txt").toPath(), "Default String Saver"))) {
				Game.log.logMessage("Score saved sucsessfully!", "Pong", "Score", "IO", "Save");
			} else {
				Game.log.logError("Score save failed!", "Pong", "Score", "IO", "Save");
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

	@Override
	public boolean isActive() {
		return true;
	}
}
