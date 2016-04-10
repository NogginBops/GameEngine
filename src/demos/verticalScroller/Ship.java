package demos.verticalScroller;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import demos.verticalScroller.projectiles.BasicProjectile;
import game.Game;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;

/**
 * @author Julius H�ger
 *
 */
public class Ship extends Sprite implements Collidable, KeyListener{
	
	private BufferedImage farLeft, left, center, right, farRight, projectile;
	
	private BufferedImage currentImage;
	
	private Rectangle movementBounds;
	
	private float movementSpeedHorizontal = 200;
	private float movementSpeedVertical = 150;
	
	//TODO: Implement scale in Sprite
	@SuppressWarnings("unused")
	private float scale = 1;
	
	private float timer = 0;
	
	private float delay = 0.1f;
	
	private boolean isSpaceDown = false;
	
	/**
	 * @param x 
	 * @param y 
	 * @param farLeft 
	 * @param left 
	 * @param center 
	 * @param right 
	 * @param farRight 
	 * @param projectile 
	 * @param image
	 * @param scale 
	 */
	public Ship(float x, float y,  BufferedImage farLeft, BufferedImage left, BufferedImage center, BufferedImage right, BufferedImage farRight, BufferedImage projectile, float scale){
		super(x, y, (int)(center.getWidth() * scale), (int)(center.getHeight() * scale));
		this.scale = scale;
		this.farLeft = farLeft;
		this.left = left;
		this.center = center;
		this.right = right;
		this.farRight = farRight;
		this.projectile = projectile;
	}
	
	/**
	 * @param movmentBounds
	 */
	public void setMovmentBounds(Rectangle movmentBounds){
		this.movementBounds = movmentBounds;
	}
	
	/**
	 * @param scale
	 */
	public void setScale(float scale){
		this.scale = scale;
		width = (int)(center.getWidth() * scale);
		height = (int)(center.getHeight() * scale);
		updateBounds();
	}

	@Override
	public void paint(Graphics2D g2d) {
		
		//This should be done another way but is fine for now
		
		if(moveLeft && !moveRight){
			if(bounds.getMinX() >= movementBounds.getMinX() + 100){
				currentImage = left;
			}else{
				currentImage = farLeft;
			}
		}else if(!moveLeft && moveRight){
			if(bounds.getMaxX() <= movementBounds.getMaxX() - 100){
				currentImage = right;
			}else{
				currentImage = farRight;
			}
		}else{
			currentImage = center;
		}
		
		
		g2d.drawImage(currentImage, (int)x, (int)y, (int)(width), (int)(height), null);
	}
	
	@Override
	public void update(long timeNano) {
		super.update(timeNano);
		
		timer += timeNano / 1000000000f;
		if(isSpaceDown){
			if(timer > delay){
				BasicProjectile projectileGO = new BasicProjectile(projectile, 2f, x + ((width - projectile.getWidth())/2), y, 0, -350);
				Game.getGameObjectHandler().addGameObject(projectileGO);
				timer = 0;
			}
		}
		
		updateBounds();
		
		if(!movementBounds.contains(bounds)){
			if (bounds.getMinX() < movementBounds.getMinX()) {
				x = (float) movementBounds.getMinX();
				dx = 0;
				
				currentImage = farRight;
			} 
			else if (bounds.getMaxX() > movementBounds.getMaxX()) {
				x = (float) movementBounds.getMaxX() - width;
				dx = 0;
				
				currentImage = farLeft;
			}
			
			if (bounds.getMinY() < movementBounds.getMinY()) {
				y = (float) movementBounds.getMinY();
				dy = 0;
			}
			else if (bounds.getMaxY() > movementBounds.getMaxY()) {
				y =  (float) movementBounds.getMaxY() - height;
				dy = 0;
			}
			
			updateBounds();
		}
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
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = true;
		}
		updateMovement();
		
		isSpaceDown  = e.getKeyCode() == KeyEvent.VK_SPACE ? true : isSpaceDown;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = false;
		}
		updateMovement();
		
		isSpaceDown = e.getKeyCode() == KeyEvent.VK_SPACE ? false : isSpaceDown;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}
}
