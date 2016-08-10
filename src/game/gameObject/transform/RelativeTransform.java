package game.gameObject.transform;

import java.awt.geom.AffineTransform;

public class RelativeTransform extends Transform {

	protected Transform transform;
	
	public RelativeTransform(Transform transform) {
		super();
		
		this.transform = transform;
	}
	
	public RelativeTransform(Transform originalTransform, Transform transform) {
		super();
		
		setPosition(originalTransform.getX(), originalTransform.getX());
		
		setRotation(originalTransform.getRotation());
		
		setScale(originalTransform.getScaleX(), originalTransform.getScaleY());
		
		this.transform = transform;
	}
	
	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform affineTransform = super.getAffineTransform();
		
		affineTransform.concatenate(transform.getAffineTransform());
		
		return affineTransform;
	}
	
}
