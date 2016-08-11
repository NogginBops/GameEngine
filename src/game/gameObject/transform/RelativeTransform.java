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
	
	/**
	 * @param originalTransform
	 * @param transform
	 */
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
