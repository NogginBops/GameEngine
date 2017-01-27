package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.UI.UI;
import game.gameObject.transform.BoxTransform;

/**
 * 
 * 
 * @author Julius Häger
 */
public abstract class UIElement {
	
	// JAVADOC: UIElement
	
	protected UI root = null;
	
	protected BoxTransform<UIElement> transform;
	
	protected int zOrder;
	
	private boolean enabled = true;
	
	/**
	 * 
	 */
	public UIElement(){
		transform = new BoxTransform<>(this);
		zOrder = 0;
	}
	
	/**
	 * 
	 * @param rect
	 */
	public UIElement(Rectangle2D rect){
		transform = new BoxTransform<>(this, (float)rect.getX(), (float)rect.getY(), (float)rect.getWidth(), (float)rect.getHeight());
		zOrder = 0;
	}
	
	/**
	 * 
	 * @param rect
	 * @param zOrder
	 */
	public UIElement(Rectangle2D rect, int zOrder){
		transform = new BoxTransform<>(this, (float)rect.getX(), (float)rect.getY(), (float)rect.getWidth(), (float)rect.getHeight());
		this.zOrder = zOrder;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width 
	 * @param height 
	 */
	public UIElement(float x, float y, float width, float height){
		transform = new BoxTransform<>(this, x, y, width, height);
		zOrder = 0;
	}
	
	/**
	 * @param parent 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public UIElement(float x, float y, float width, float height, int zOrder) {
		transform = new BoxTransform<>(this, x, y, width, height);
		this.zOrder = zOrder;
	}
	
	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);
	
	/**
	 * @return
	 */
	public abstract BufferedImage getImage();
	
	/**
	 * @return
	 */
	public UI getRoot()
	{
		return root;
	}
	
	/**
	 * @return
	 */
	public int getZOrder(){
		return zOrder;
	}
	
	/**
	 * @param zOrder
	 */
	public void setZOrder(int zOrder){
		this.zOrder = zOrder;
	}
	
	/**
	 * @return
	 */
	public float getX(){
		return transform.getX();
	}
	
	/**
	 * @return
	 */
	public float getY(){
		return transform.getY();
	}
	
	/**
	 * @param x
	 */
	public void setX(float x){
		transform.setX(x);
	}
	
	/**
	 * @param y
	 */
	public void setY(float y){
		transform.setY(y);
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		transform.setPosition(x, y);
	}
	
	/**
	 * @return
	 */
	public float getWidth(){
		return transform.getWidth();
	}
	
	/**
	 * @return
	 */
	public float getHeight(){
		return transform.getHeight();
	}
	
	/**
	 * @param width
	 */
	public void setWidth(float width){
		transform.setWidth(width);
	}
	
	/**
	 * @param height
	 */
	public void setHeight(float height){
		transform.setHeight(height);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height){
		transform.setSize(width, height);
	}
	
	/**
	 * @param rect
	 */
	public void setRect(Rectangle2D rect){
		transform.setPosition((float)rect.getX(), (float)rect.getY());
		transform.setSize((float)rect.getWidth(), (float)rect.getHeight());
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setRect(float x, float y, float width, float height){
		transform.setPosition(x, y);
		transform.setSize(width, height);
	}
	
	/**
	 * @return
	 */
	public boolean isEnabled(){
		return enabled;
	}
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	/**
	 * @return
	 */
	public BoxTransform<UIElement> getTransform(){
		return transform;
	}
}
