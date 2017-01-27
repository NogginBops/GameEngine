package game.gameObject.transform;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import game.util.math.MathUtils;
import game.util.math.vector.Vector2D;

/**
 * @author Julius H�ger
 * @param <T> 
 *
 */
public class Transform<T> {
	
	//JAVADOC: Transform
	
	//TODO: Root transform
	
	protected T object;
	
	protected Transform<T> parent;
	
	protected ArrayList<Transform<T>> children;
	
	protected float x;
	
	protected float y;
	
	protected float scaleX;
	
	protected float scaleY;
	
	protected float rotation;
	
	protected AffineTransform affineTransform = new AffineTransform();
	
	/**
	 * @param object 
	 * 
	 */
	public Transform(T object){
		x = 0;
		y = 0;
		
		scaleX = 1;
		scaleY = 1;
		
		rotation = 0;
		
		children = new ArrayList<>();
		
		this.object = object;
	}
	
	/**
	 * @param object 
	 * @param parent
	 */
	public Transform(T object, Transform<T> parent) {
		x = 0;
		y = 0;
		
		scaleX = 1;
		scaleY = 1;
		
		rotation = 0;
		
		this.parent = parent;
		
		children = new ArrayList<>();
		
		this.object = object;
	}
	
	/**
	 * @param object 
	 * @param parent 
	 * @param orig 
	 */
	public Transform(T object, Transform<T> parent, Transform<T> orig){
		this(object, parent);
		
		this.x = orig.x;
		this.y = orig.y;
		
		this.scaleX = orig.scaleX;
		this.scaleY = orig.scaleY;
		
		this.rotation = orig.rotation;
		
		children = new ArrayList<>();
	}
	
	/**
	 * @return
	 */
	public AffineTransform getAffineTransform(){
		affineTransform.setToIdentity();
		
		affineTransform.translate(x, y);
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation));
		
		if(parent != null){
			affineTransform.concatenate(parent.getAffineTransform());
		}
		
		return affineTransform;
	}
	
	/**
	 * @return
	 */
	public T getObject(){
		return object;
	}
	
	/**
	 * @return
	 */
	public Transform<T> getParent(){
		return parent;
	}
	
	/**
	 * @param parent
	 */
	public void setParent(Transform<T> parent){
		if (this.parent == parent) return;
		
		this.parent.removeChild(this);
		this.parent = parent;
		
		if (parent != null) {
			parent.addChild(this);	
		}
	}
	
	/**
	 * @param child
	 */
	public void addChild(Transform<T> child){
		children.add(child);
		child.setParent(this);
	}
	
	/**
	 * @param child
	 */
	public void removeChild(Transform<T> child){
		children.remove(child);
		child.setParent(null);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Transform<T>> getChildren(){
		return new ArrayList<>(children);
	}
	
	/**
	 * @param i
	 * @return
	 */
	public Transform<T> getChild(int i){
		if(i < 0 || i >= children.size()){
			return null;
		}
		
		return children.get(i);
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
	 * @return
	 */
	public Vector2D getPosition(){
		return new Vector2D(getX(), getY());
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
