package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.UI.UI;
import game.UI.elements.containers.UIContainer;

/**
 * 
 * 
 * @author Julius Häger
 */
public abstract class UIElement{
	
	// JAVADOC: UIElement
	
	//TODO: Should this actually be a GameObject
	
	protected UI root;
	
	protected UIContainer parent;

	protected Rectangle2D area;

	protected int zOrder = 10;

	/**
	 * 
	 */
	public UIElement() {
		area = new Rectangle2D.Float();
	}

	/**
	 * @param area
	 */
	public UIElement(Rectangle2D area) {
		this.area = area;
	}

	/**
	 * @param width
	 * @param height
	 */
	public UIElement(float width, float height) {
		area = new Rectangle2D.Float(0, 0, width, height);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public UIElement(float x, float y, float width, float height){
		area = new Rectangle2D.Float(x, y, width, height);
	}
	
	/**
	 * @param parent
	 */
	public void setParent(UIContainer parent){
		if(this.parent != parent){
			if(parent == null){
				this.parent = null;
				setRoot(null);
			}else{
				if(root != null && root.contains(parent)){
					this.parent.parent = parent;
				}else{
					setRoot(parent.root);
					this.parent = parent;
				}
			}
		}
		
		/*
		if(this.parent != null){
			//This set root to null!
			this.parent.removeUIElement(this);
		}
		
		if(parent.root != this.root){
			root = parent.getRoot();
		}
		
		if(parent != root && !root.contains(parent)){
			root = parent.getRoot();
		}
		*/
	}
	
	/**
	 * @return
	 */
	public UIContainer getParent(){
		return parent;
	}
	
	/**
	 * @param root
	 */
	public void setRoot(UI root){
		
		if(this.root != root){
			if(root == null){
				this.root = null;
				if(this.parent != null){
					this.parent = null;
				}
			}else{
				this.root = root;
				if(!root.contains(parent)){
					setParent(root);
				}
			}
		}
	}
	
	/**
	 * @return
	 */
	public UI getRoot(){
		return root;
	}
	
	/**
	 * @param z
	 */
	public void setZOrder(int z){
		zOrder = z;
	}

	/**
	 * @return
	 */
	public int getZOrder() {
		return zOrder;
	}
	
	/**
	 * @return
	 */
	public Rectangle2D getArea(){
		return area;
	}
	
	/**
	 * @return
	 */
	public Rectangle2D getBounds() {
		//TODO: Pre compute?
		Rectangle2D parentArea = parent.getBounds();
		return new Rectangle2D.Float((float)area.getX() + (float)parentArea.getX(), (float)area.getY() + (float)parentArea.getY(), (float)area.getWidth(), (float)area.getHeight());
	}
	
	/**
	 * @return
	 */
	public float getX(){
		return (float)area.getX();
	}
	
	/**
	 * @param x
	 */
	public void setX(float x){
		area.setFrame(x, area.getY(), area.getWidth(), area.getHeight());
	}
	
	/**
	 * @return
	 */
	public float getY(){
		return (float)area.getY();
	}
	
	/**
	 * @param y
	 */
	public void setY(float y){
		area.setFrame(area.getX(), y, area.getWidth(), area.getHeight());
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		area.setFrame(x, y, area.getWidth(), area.getHeight());
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setWidth(float width){
		area.setFrame(area.getX(), area.getY(), width, area.getHeight());
	}
	
	/**
	 * @return
	 */
	public float getWidth(){
		return (float)area.getWidth();
	}
	
	/**
	 * 
	 * @param height
	 */
	public void setHeight(float height){
		area.setFrame(area.getX(), area.getY(), area.getWidth(), height);
	}
	
	/**
	 * @return
	 */
	public float getHeight(){
		return (float)area.getHeight();
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height){
		area.setFrame(area.getX(), area.getY(), width, height);
	}
	
	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

}
