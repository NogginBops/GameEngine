package game.gameObject.graphics.breakout;

import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Julius Häger
 *
 */
public class Brick extends Sprite implements Collidable{
	
	private Color color = Color.PINK;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public Brick(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;	
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(bounds);
	}

	@Override
	public int getMass() {
		return 0;
	}

	@Override
	public void willCollide(Collidable collisionObject) {
		
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}

	@Override
	public void willNoLongerCollide(Collidable collidedObject) {
		
	}

	@Override
	public void noLongerColliding(Collidable collidedObject) {
		
	}
}
