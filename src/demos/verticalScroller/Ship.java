package demos.verticalScroller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Ship extends Sprite implements Collidable, KeyListener{

	private BufferedImage image;
	
	private float movementSpeedHorizontal = 150;
	private float movementSpeedVertical = 100;
	
	private float scale = 1;
	
	/**
	 * @param x 
	 * @param y 
	 * @param image
	 * @param scale 
	 */
	public Ship(float x, float y, BufferedImage image, float scale){
		super(x, y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale));
		this.scale = scale;
		this.image = image;
	}
	
	/**
	 * @param scale
	 */
	public void setScale(float scale){
		this.scale = scale;
		width = (int)(image.getWidth() * scale);
		height = (int)(image.getHeight() * scale);
		updateBounds();
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(image, (int)x, (int)y, (int)(width), (int)(height), null);
	}
	
	private boolean moveLeft, moveRight, moveUp, moveDown;
	
	private void updateMovement() {
		int dx = 0;
		dx += moveLeft ? -movementSpeedHorizontal : 0;
		dx += moveRight ? movementSpeedHorizontal : 0;
		setDX(dx);
		int dy = 0;
		dy += moveUp ? -movementSpeedVertical : 0;
		dy += moveDown ? movementSpeedVertical : 0;
		setDY(dy);
	}
	
	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown = true;
		}
		updateMovement();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown = false;
		}
		updateMovement();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return false;
	}
}
