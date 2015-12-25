package demos.verticalScroller.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;

public abstract class Projectile extends Sprite implements Collidable{

	protected BufferedImage image;
	
	public Projectile(float x, float y, BufferedImage image) {
		super(x, y, image.getWidth(), image.getHeight());
		this.image = image;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}
}
