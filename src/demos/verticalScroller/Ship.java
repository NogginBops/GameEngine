package demos.verticalScroller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Ship extends Sprite implements Collidable, KeyListener{

	private BufferedImage image;
	
	private float movementSpeed = 100;
	
	private float scale = 1;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Ship(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		loadRecources();
	}
	
	/**
	 * @param x 
	 * @param y 
	 * @param image
	 * @param scale 
	 */
	public Ship(float x, float y, BufferedImage image, float scale){
		super(x, y, image.getWidth(), image.getHeight());
		this.scale = scale;
		this.image = image;
	}
	
	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
	}
	
	//TODO: Sprite sheet support
	
	private void loadRecources(){
		try {
			image = IOHandler.load(new LoadRequest<BufferedImage>("Ship", new File("./res/verticalScroller/Ship.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(image, (int)x, (int)y, (int)(width * scale), (int)(height * scale), null);
	}
	
	private boolean moveLeft, moveRight, moveUp, moveDown;
	
	private void updateMovement() {
		int dx = 0;
		dx += moveLeft ? -movementSpeed : 0;
		dx += moveRight ? movementSpeed : 0;
		setDX(dx);
		int dy = 0;
		dy += moveUp ? -movementSpeed : 0;
		dy += moveDown ? movementSpeed : 0;
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
		return true;
	}
}
