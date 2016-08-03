package game.gameObject.transform;

import java.awt.geom.AffineTransform;

import game.util.math.MathUtils;

public class Transform {
	
	protected float x;
	
	protected float y;
	
	protected float scaleX;
	
	protected float scaleY;
	
	protected float rotation;
	
	public Transform() {
		x = 0;
		y = 0;
		
		scaleX = 1;
		scaleY = 1;
		
		rotation = 0;
	}
	
	public Transform(Transform transform){
		x = transform.x;
		y = transform.y;
		
		scaleX = transform.scaleX;
		scaleY = transform.scaleY;
		
		rotation = transform.rotation;
	}
	
	public AffineTransform getAffineTransform(){
		AffineTransform affineTransform = new AffineTransform();
		
		affineTransform.translate(x, y);
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation), 0, 0);
		
		return affineTransform;
	}
	
	public AffineTransform getUntranslatedTransform(){
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation), x, y);
		
		return affineTransform;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void translate(float x, float y){
		this.x += x;
		this.y += y;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	
	public void setScale(float scaleX, float scaleY){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public void scale(float scaleX, float scaleY){
		this.scaleX += scaleX;
		this.scaleY += scaleY;
	}

	public float getRotation() {
		return rotation;
	}
	
	public float getRotationRad(){
		return (float) Math.toRadians(rotation);
	}

	public void setRotation(float rotation) {
		this.rotation = MathUtils.wrap(rotation, 0, 360);
	}
	
	public void rotate(float rotation){
		this.rotation = MathUtils.wrap(this.rotation + rotation, 0, 360);
	}
}
