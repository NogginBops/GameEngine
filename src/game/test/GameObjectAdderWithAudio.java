package game.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.Random;

import game.Game;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;
import game.input.mouse.MouseListener;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;

/**
 * @author Julius Häger
 *
 */
public class GameObjectAdderWithAudio extends BasicGameObject implements Paintable, MouseListener {
	
	//TODO: Remove/Relocate

	private Random rand;

	private Sound sound;

	/**
	 * @param x
	 * @param y
	 * @param objectHandler
	 */
	public GameObjectAdderWithAudio(int x, int y) {
		super(x, y, new Rectangle2D.Float(0, 0, 10, 10), Integer.MAX_VALUE - 10);
		rand = new Random();
		
		sound = IOHandler.load(new LoadRequest<Sound>("Robot sound", Paths.get("./res/robot.mp3"), Sound.class)).result;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		OtherPaintable p = new OtherPaintable(e.getX(), e.getY(), rand.nextInt(200), rand.nextInt(200), 0,
				new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		Game.gameObjectHandler.addGameObject(p);
		AudioSource source = new AudioSource(e.getX(), e.getY(), sound);
		AudioEngine.playSound(source);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public boolean absorb() {
		return false;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return true;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawRect(0, 0, (int)getWidth(), (int)getWidth());
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	@Override
	public Rectangle2D getBounds() {
		return super.getBounds();
	}
}
