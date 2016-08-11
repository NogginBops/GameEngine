package game.gameObject.physics;

import java.awt.Shape;

<<<<<<< HEAD
/**
 * @author Julius Häger
 *
 */
=======
import game.gameObject.transform.Transform;

>>>>>>> origin/TransformBranch
public abstract class BasicRotatable extends BasicMovable implements Rotatable {
	
	//JAVADOC: BasicRotatable
	
	protected float dr = 0;
	
<<<<<<< HEAD
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 * @param rotation
	 */
=======
	public BasicRotatable(Transform transform, Shape shape, int zOrder, float rotation) {
		super(transform, shape, zOrder);
	}
	
>>>>>>> origin/TransformBranch
	public BasicRotatable(float x, float y, float width, float height, int zOrder, float rotation) {
		super(x, y, width, height, zOrder);
	}

	/**
	 * @param x
	 * @param y
	 * @param shape
	 * @param zOrder
	 * @param rotation
	 */
	public BasicRotatable(float x, float y, Shape shape, int zOrder, float rotation) {
		super(x, y, shape, zOrder);
	}
	
	@Override
	public float getDR() {
		return dr;
	}
	
	@Override
	public void setDR(float dr) {
		this.dr = dr;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		transform.rotate(dr * deltaTime);
	}
	
	@Override
	public String[] getDebugValues() {
		String[] superValues = super.getDebugValues();
		String[] ownValues = new String[]{
				"<b>Rotation:</b> " + transform.getRotation(),
				"<b>DR:</b> " + dr
		};
		
		String[] mergedValues = new String[superValues.length + ownValues.length];
		System.arraycopy(superValues, 0, mergedValues, 0, superValues.length);
		System.arraycopy(ownValues, 0, mergedValues, superValues.length, ownValues.length);
		return mergedValues;
	}
	
}
