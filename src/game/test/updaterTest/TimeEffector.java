package game.test.updaterTest;

import game.gameObject.physics.Collidable;

/**
 * @author Julius Häger
 *
 */
public interface TimeEffector extends Collidable{
	
	/**
	 * @return
	 */
	public float getTimeMultiplier();
	
}
