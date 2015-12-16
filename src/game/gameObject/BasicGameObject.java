package game.gameObject;

import java.awt.Rectangle;

public class BasicGameObject implements GameObject {
	
	/**
	 * 
	 */
	protected float x;
	/**
	 * 
	 */
	protected float y;
	
	/**
	 * 
	 */
	protected float width;
	/**
	 * 
	 */
	protected float height;
	
	protected Rectangle bounds;
	
	protected int zOrder;
	
	public BasicGameObject(int x, int y, int width, int height, int zOrder) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(x, y, width, height);
		this.zOrder = zOrder;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {
		bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public int getZOrder() {
		return zOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if(zOrder == object.getZOrder()){
			return 0;
		}else{
			return zOrder > object.getZOrder() ? 1 : -1;
		}
	}
}
