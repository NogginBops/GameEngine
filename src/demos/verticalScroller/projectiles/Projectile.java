package demos.verticalScroller.projectiles;

import java.awt.image.BufferedImage;

import game.Game;
import game.gameObject.graphics.Sprite;
import game.gameObject.physics.Collidable;

/**
 * @author Julius Häger
 *
 */
public abstract class Projectile extends Sprite implements Collidable{

	protected float lifetime;
	
	private float timer;
	
	/**
	 * @param x
	 * @param y
	 * @param image
	 * @param lifetime
	 */
	public Projectile(float x, float y, BufferedImage image, float lifetime) {
		super(x, y, image.getWidth(), image.getHeight());
		setSprite(image);
		this.lifetime = lifetime;
		timer = 0;
	}
	
	@Override
	public void update(long timeNano) {
		super.update(timeNano);
		timer += timeNano / 1000000000f;
		if(timer > lifetime){
			Game.getGameObjectHandler().removeGameObject(this);
		}
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}
}
