package game.gameObject.physics;

import java.awt.Shape;

import game.gameObject.transform.Transform;

public abstract class BasicRotatable extends BasicMovable implements Rotatable {
	
	protected float dr = 0;
	
	public BasicRotatable(Transform transform, Shape shape, int zOrder, float rotation) {
		super(transform, shape, zOrder);
	}
	
	public BasicRotatable(float x, float y, float width, float height, int zOrder, float rotation) {
		super(x, y, width, height, zOrder);
	}

	public BasicRotatable(float x, float y, Shape shape, int zOrder, float rotation) {
		super(x, y, shape, zOrder);
	}
	
	@Override
	public float getDR() {
		return dr;
	}
	
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
