package demos.verticalScroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import demos.verticalScroller.projectiles.BasicProjectile;
import game.Game;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;
import game.input.keys.KeyListener;

/**
 * @author Julius Häger
 *
 */
public class Ship extends Sprite implements Collidable, KeyListener{
	
	private BufferedImage farLeft, left, center, right, farRight, projectile;
	
	private float movementSpeedHorizontal = 200;
	private float movementSpeedVertical = 150;
	
	private float scale = 1;
	
	private float timer = 0;
	
	private float delay = 0.1f;
	
	private boolean isSpaceDown = false;
	
	/**
	 * @param x 
	 * @param y 
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
		if(moveLeft && !moveRight){
			g2d.drawImage(left, (int)x, (int)y, (int)(width), (int)(height), null);
		}else if(!moveLeft && moveRight){
			g2d.drawImage(right, (int)x, (int)y, (int)(width), (int)(height), null);
		}else{
			g2d.drawImage(center, (int)x, (int)y, (int)(width), (int)(height), null);
		}
	}
	
	@Override
	public void update(long timeNano) {
		super.update(timeNano);
		timer += timeNano / 1000000000f;
		if(isSpaceDown){
			if(timer > delay){
				BasicProjectile projectileGO = new BasicProjectile(projectile, x + ((width - projectile.getWidth())/2), y, 0, -350);
				Game.getGameObjectHandler().addGameObject(projectileGO);
				Game.game.addUpdateListener(projectileGO);
				timer = 0;
			}
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
		
		isSpaceDown  = e.getKeyCode() == KeyEvent.VK_SPACE ? true : isSpaceDown;
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			
		/*	if(timer > delay){
				BasicProjectile projectileGO = new BasicProjectile(projectile, x + ((width - projectile.getWidth())/2), y, 0, -200);
				Game.getGameObjectHandler().addGameObject(projectileGO);
				Game.game.addUpdateListener(projectileGO);
				timer = 0;
			}*/
		}
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
