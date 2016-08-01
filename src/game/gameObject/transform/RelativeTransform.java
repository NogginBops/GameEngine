package game.gameObject.transform;

import java.awt.geom.AffineTransform;

public class RelativeTransform extends Transform {

	protected Transform transform;
	
	public RelativeTransform(Transform transform) {
		super();
		
		this.transform = transform;
	}
	
	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform affineTransform = super.getAffineTransform();
		
		affineTransform.concatenate(transform.getAffineTransform());
		
		return affineTransform;
	}
	
}
