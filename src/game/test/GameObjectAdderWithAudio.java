package game.test;

import game.gameObject.GameObject;
import game.gameObject.graphics.Paintable;
import game.gameObject.handler.GameObjectHandler;
import game.input.mouse.MouseListener;
import game.sound.AudioEngine;
import game.sound.AudioSource;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * @author Julius Häger
 *
 */
public class GameObjectAdderWithAudio implements GameObject, Paintable, MouseListener {
	
	//TODO: Remove/Relocate

	private int ZOrder = Integer.MAX_VALUE - 8;

	private GameObjectHandler gameObjectHandler;

	private Random rand;

	private Rectangle bounds = new Rectangle(10, 10);

	private Sound sound;

	/**
	 * @param x
	 * @param y
	 * @param objectHandler
	 */
	public GameObjectAdderWithAudio(int x, int y, GameObjectHandler objectHandler) {
		gameObjectHandler = objectHandler;
		rand = new Random();
		sound = TinySound.loadSound(new File("./res/robot.mp3"));
		bounds = new Rectangle(x, y, 10, 10);
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {

	}

	@Override
	public int getZOrder() {
		return ZOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if (ZOrder == object.getZOrder()) {
			return 0;
		} else {
			return ZOrder > object.getZOrder() ? 1 : -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		OtherPaintable p = new OtherPaintable(e.getX(), e.getY(), rand.nextInt(200), rand.nextInt(200), 0,
				new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		gameObjectHandler.addGameObject(p);
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
	public void mouseWeelMoved(MouseWheelEvent e) {

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
		g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
