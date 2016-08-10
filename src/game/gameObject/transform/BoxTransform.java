package game.gameObject.transform;

import java.awt.geom.AffineTransform;

import game.util.math.MathUtils;

public class BoxTransform extends Transform {
	
	protected float width;
	
	protected float height;
	
	protected float anchorX;
	
	protected float anchorY;
	
	public BoxTransform() {
		super();
		
		width = 0;
		height = 0;
		
		anchorX = 0;
		anchorY = 0;
	}
	
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
		
		affineTransform.translate(x + (width * anchorX), y + (height * anchorY));
		
		//affineTransform.translate((width * anchorX), (height * anchorY));
		
		affineTransform.scale(scaleX, scaleY);
		
		affineTransform.rotate(Math.toRadians(rotation));
		
		affineTransform.translate(-((width * anchorX)), -((height * anchorY)));
		
		return affineTransform;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getAnchorX() {
		return anchorX;
	}

	public void setAnchorX(float anchorX) {
		this.anchorX = MathUtils.clamp(anchorX, 0, 1);
	}

	public float getAnchorY() {
		return anchorY;
	}

	public void setAnchorY(float anchorY) {
		this.anchorY = MathUtils.clamp(anchorY, 0, 1);
	}

}
