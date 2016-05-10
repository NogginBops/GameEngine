package demos.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import demos.pong.Pad.Side;
import demos.pong.event.PlayerScoreEvent;
import game.Game;
import game.IO.IOHandler;
import game.IO.save.SaveRequest;
import game.UI.UI;
import game.UI.elements.image.UIRect;
import game.UI.elements.text.UILabel;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Score extends UI implements KeyListener {

	private int left = 0;

	private int right = 0;

	private Rectangle deviderRect;

	private int deviderWidth = 4;
	
	private UILabel player1Score, player2Score;
	
	private UIRect devider;

	@SuppressWarnings("unused")
	private BufferedImage img;

	/**
	 * @param area
	 */
	public Score(Rectangle area) {
		super(area);
		deviderRect = new Rectangle((int) (area.getWidth() / 2 - deviderWidth / 2), area.y, deviderWidth,
				area.height);
		
		devider = new UIRect(deviderRect, Color.WHITE);
		
		setZOrder(0);
		
		player1Score = new UILabel("Player 1: 0");
		player1Score.setPosition(devider.getX() - 55, 20);
		player2Score = new UILabel("Player 2: 0");
		player2Score.setPosition(devider.getX() + 50, 20);
		
		addUIElements(player1Score, player2Score, devider);
		
		Game.eventMachine.addEventListener(PlayerScoreEvent.class, new EventListener() {
			
			@Override
			public <T extends GameEvent<?>> void eventFired(T event) {
				if(event instanceof PlayerScoreEvent){
					if(((PlayerScoreEvent)event).side == Side.RIGHT){
						right++;
					}else{
						left++;
					}
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
							String.class, new File("./res/Score.txt"), "Default String Saver"))) {
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
}
