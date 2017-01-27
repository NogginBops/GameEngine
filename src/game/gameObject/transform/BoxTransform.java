package game.gameObject.transform;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import game.util.math.MathUtils;
import game.util.math.vector.Vector2D;

/**
 * @author Julius Häger
 * @param <T> 
 *
 */
public class BoxTransform<T> extends Transform<T> {
	
	//JAVADOC: BoxTransform
	
	protected float width;
	
	protected float height;
	
	protected float anchorX;
	
	protected float anchorY;
	
	/**
	 * @param object 
	 * 
	 */
	public BoxTransform(T object) {
		super(object);
		
		width = 0;
		height = 0;
		
		anchorX = 0;
		anchorY = 0;
	}
	
	/**
	 * @param object 
	 * @param parent 
	 * 
	 */
	public BoxTransform(T object, Transform<T> parent) {
		super(object, parent);
		
		width = 0;
		height = 0;
		
		anchorX = 0;
		anchorY = 0;
	}
	
	/**
	 * @param object 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BoxTransform(T object, float x, float y, float width, float height){
		super(object);
		
		translate(x, y);
		
		this.width = width; 
		this.height = height;
		
		//NOTE: 0 or 0.5f?
		this.anchorX = 0;
		this.anchorY = 0;
	}
	
	/**
	 * @param object 
	 * @param parent 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BoxTransform(T object, Transform<T> parent, float x, float y, float width, float height){
		super(object, parent);
		
		translate(x, y);
		
		this.width = width; 
		this.height = height;
		
		//NOTE: 0 or 0.5f?
		this.anchorX = 0;
		this.anchorY = 0;
	}
	
	/**
	 * @param object 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchorX
	 * @param anchorY
	 */
	public BoxTransform(T object, float x, float y, float width, float height, float anchorX, float anchorY){
		super(object);
		
		translate(x - (width * anchorX), y - (height * anchorY));
		
		this.width = width; 
		this.height = height;
		
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	/**
	 * @param object 
	 * @param parent 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchorX
	 * @param anchorY
	 */
	public BoxTransform(T object, Transform<T> parent, float x, float y, float width, float height, float anchorX, float anchorY){
		super(object, parent);
		
		translate(x - (width * anchorX), y - (height * anchorY));
		
		this.width = width; 
		this.height = height;
		
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	@Override
	public AffineTransform getAffineTransform() {
		affineTransform.setToIdentity();
		
		affineTransform.translate(x + (width * anchorX), y + (height * anchorY));
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation));
		
		affineTransform.translate(-((width * anchorX)), -((height * anchorY)));
		
		if(parent != null){
			affineTransform.concatenate(parent.getAffineTransform());
		}
		
		return affineTransform;
	}
	
	@Override
	public float getX() {
		return x + (width * anchorX);
	}
	
	@Override
	public float getY() {
		return y + (height * anchorY);
	}
	
	/**
	 * @return
	 */
	public Rectangle2D getRect(){
		return new Rectangle2D.Float(x, y, width, height);
	}
	
	/**
	 * 
	 * @return
	 */
	public Shape getTransformedRect(){
		return getAffineTransform().createTransformedShape(getRect());
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
	public Vector2D getSize(){
		return new Vector2D(getWidth(), getHeight());
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height){
		setWidth(width);
		setHeight(height);
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
	
	/**
	 * @return
	 */
	public float getOffsetX(){
		return width * anchorX;
	}
	
	/**
	 * @return
	 */
	public float getOffsetY(){
		return height * anchorY;
	}
}
