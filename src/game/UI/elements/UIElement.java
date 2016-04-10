package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.UI.UI;
import game.UI.elements.containers.UIContainer;

/**
 * 
 * 
 * @author Julius H�ger
 */
public abstract class UIElement{

	// TODO: UIElement

	// JAVADOC: UIElement
	
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
		if(parent != root && !root.contains(parent)){
			root = parent.getRoot();
		}
		if(parent != this.parent){
			if(this.parent != null){
				this.parent.removeUIElement(this);
			}
			this.parent = parent;
		}
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
		if(root != null && !root.contains(this)){
			setParent(root);
		}
		this.root = root;
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
	 * @return
	 */
	public int getWidth(){
		return area.width;
	}
	
	/**
	 * @return
	 */
	public int getHeight(){
		return area.height;
	}
	
	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

}
