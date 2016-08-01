package game.gameObject.physics;

import game.util.math.MathUtils;

public interface Rotatable extends Movable {
	
	default public float getRotation(){
		return getTransform().getRotation();
	}
	
	default public void setRotation(float deg){
		getTransform().setRotation((float) Math.toRadians(MathUtils.wrap(deg, 0, 360)));
	}
	
	public float getDR();
	
	public void setDR(float dr);

}
