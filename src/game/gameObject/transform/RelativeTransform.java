package game.gameObject.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Julius Häger
 *
 */
public class RelativeTransform extends Transform {

	//JAVADOC: RelativeTransform
	
	//TODO: Does this class even work?
	
	protected Transform transform;
	
	/**
	 * @param transform
	 */
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
