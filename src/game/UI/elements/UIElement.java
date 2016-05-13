package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;

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

	protected Rectangle area;

	protected int zOrder = 10;

	/**
	 * 
	 */
	public UIElement() {
		area = new Rectangle();
	}

	/**
	 * @param area
	 */
	public UIElement(Rectangle area) {
		this.area = area;
	}

	/**
	 * @param width
	 * @param height
	 */
	public UIElement(int width, int height) {
		area = new Rectangle(width, height);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public UIElement(int x, int y, int width, int height){
		area = new Rectangle(x, y, width, height);
	}
	
	/**
	 * @param parent
	 */
	public void setParent(UIContainer parent){
		//FIXME: UI NOT WORKING!!!!
		//Something about removing itself after removing itself from its parent.
		
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
	public Rectangle getArea(){
		return area;
	}
	
	/**
	 * @return
	 */
	public Rectangle getBounds() {
		
		//TODO: Pre compute?
		Rectangle parentArea = parent.getBounds();
		return new Rectangle(area.x + parentArea.x, area.y + parentArea.y, area.width, area.height);
	}
	
	/**
	 * @return
	 */
	public int getX(){
		return area.x;
	}
	
	/**
	 * @param x
	 */
	public void setX(int x){
		area.setLocation(x, (int) area.getY());
	}
	
	/**
	 * @return
	 */
	public int getY(){
		return area.y;
	}
	
	/**
	 * @param y
	 */
	public void setY(int y){
		area.setLocation((int)area.getX(), y);
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		area.setLocation(x, y);
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setWidth(int width){
		area.width = width;
	}
	
	/**
	 * @return
	 */
	public int getWidth(){
		return area.width;
	}
	
	/**
	 * 
	 * @param height
	 */
	public void setHeight(int height){
		area.height = height;
	}
	
	/**
	 * @return
	 */
	public int getHeight(){
		return area.height;
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height){
		area.setSize(width, height);
	}
	
	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

}
