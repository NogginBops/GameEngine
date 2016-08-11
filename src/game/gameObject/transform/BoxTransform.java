package game.gameObject.transform;

import java.awt.geom.AffineTransform;

import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class BoxTransform extends Transform {
	
	//JAVADOC: BoxTransform
	
	protected float width;
	
	protected float height;
	
	protected float anchorX;
	
	protected float anchorY;
	
	/**
	 * 
	 */
	public BoxTransform() {
		super();
		
		width = 0;
		height = 0;
		
		anchorX = 0;
		anchorY = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchorX
	 * @param anchorY
	 */
	public BoxTransform(float x, float y, float width, float height, float anchorX, float anchorY){
		super();
		
		translate(x, y);
		
		this.width = width; 
		this.height = height;
		
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform affineTransform = new AffineTransform();
		
		affineTransform.translate(x - (width * anchorX), y - (height * anchorY));
		
		affineTransform.translate((width * anchorX), (height * anchorY));
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation));
		
		affineTransform.translate(-((width * anchorX)), -((height * anchorY)));
		
		return affineTransform;
	}

	/**
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return
	 */
	public float getAnchorX() {
		return anchorX;
	}

	/**
	 * @param anchorX
	 */
	public void setAnchorX(float anchorX) {
		this.anchorX = MathUtils.clamp(anchorX, 0, 1);
	}

	/**
	 * @return
	 */
	public float getAnchorY() {
		return anchorY;
	}

	/**
	 * @param anchorY
	 */
	public void setAnchorY(float anchorY) {
		this.anchorY = MathUtils.clamp(anchorY, 0, 1);
	}

}
