package game.gameObject.transform;

import java.awt.geom.AffineTransform;

import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class Transform {
	
	//JAVADOC: Transform
	
	protected float x;
	
	protected float y;
	
	protected float scaleX;
	
	protected float scaleY;
	
	protected float rotation;
	
	/**
	 * 
	 */
	public Transform() {
		x = 0;
		y = 0;
		
		scaleX = 1;
		scaleY = 1;
		
		rotation = 0;
	}
	
	/**
	 * @param transform
	 */
	public Transform(Transform transform){
		x = transform.x;
		y = transform.y;
		
		scaleX = transform.scaleX;
		scaleY = transform.scaleY;
		
		rotation = transform.rotation;
	}
	
	/**
	 * @return
	 */
	public AffineTransform getAffineTransform(){
		AffineTransform affineTransform = new AffineTransform();
		
		affineTransform.translate(x, y);
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation));
		
		return affineTransform;
	}
	
	/**
	 * @return
	 */
	public AffineTransform getUntranslatedTransform(){
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation), x, y);
		
		return affineTransform;
	}

	/**
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void translate(float x, float y){
		this.x += x;
		this.y += y;
	}

	/**
	 * @return
	 */
	public float getScaleX() {
		return scaleX;
	}

	/**
	 * @param scaleX
	 */
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @return
	 */
	public float getScaleY() {
		return scaleY;
	}

	/**
	 * @param scaleY
	 */
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	
	/**
	 * @param scaleX
	 * @param scaleY
	 */
	public void setScale(float scaleX, float scaleY){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	/**
	 * @param scaleX
	 * @param scaleY
	 */
	public void scale(float scaleX, float scaleY){
		this.scaleX += scaleX;
		this.scaleY += scaleY;
	}

	/**
	 * @return
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * @return
	 */
	public float getRotationRad(){
		return (float) Math.toRadians(getRotation());
	}

	/**
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = MathUtils.wrap(rotation, 0, 360);
	}
	
	/**
	 * @param rotation
	 */
	public void rotate(float rotation){
		this.rotation = MathUtils.wrap(this.rotation + rotation, 0, 360);
	}
}
