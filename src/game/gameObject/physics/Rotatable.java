package game.gameObject.physics;

import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public interface Rotatable extends Movable {
	
	//TODO: Move this to the Movable interface!
	
	/**
	 * @return
	 */
	default public float getRotation(){
		return getTransform().getRotation();
	}
	
<<<<<<< HEAD
	/**
	 * @param deg
	 */
=======
	default public float getRotationRad(){
		return getTransform().getRotationRad();
	}
	
>>>>>>> origin/TransformBranch
	default public void setRotation(float deg){
		getTransform().setRotation((float) Math.toRadians(MathUtils.wrap(deg, 0, 360)));
	}
	
	/**
	 * @return
	 */
	public float getDR();
	
	/**
	 * @param dr
	 */
	public void setDR(float dr);

}
