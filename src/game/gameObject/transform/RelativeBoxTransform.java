package game.gameObject.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Julius H�ger
 *
 */
public class RelativeBoxTransform extends BoxTransform {
	
	//FIXME: Make transforms relative by default!
	
	protected Transform transform;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param transform
	 */
	public RelativeBoxTransform(float x, float y, float width, float height, Transform transform) {
		super(x, y, width, height, 0, 0);
		
		this.transform = transform;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchorX
	 * @param anchorY
	 * @param transform
	 */
	public RelativeBoxTransform(float x, float y, float width, float height, float anchorX, float anchorY, Transform transform) {
		super(x, y, width, height, anchorX, anchorY);
		
		this.transform = transform;
	}
	
	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform affineTransform = new AffineTransform();
		
		affineTransform.translate(getX(), getY());
		
		affineTransform.scale(getScaleX(), getScaleY());
		
		affineTransform.rotate(getRotationRad());
		
		affineTransform.translate(-((width * anchorX)), -((height * anchorY)));
		
		return affineTransform;
	}
	
	@Override
	public float getX() {
		return super.getX() + transform.getX();
	}
	
	@Override
	public float getY() {
		return super.getY() + transform.getY();
	}
	
	@Override
	public float getRotation() {
		return super.getRotation() + transform.getRotation();
	}
	
	@Override
	public float getScaleX() {
		return super.getScaleX() * transform.getScaleX();
	}
	
	@Override
	public float getScaleY() {
		return super.getScaleY() * transform.getScaleY();
	}
}
