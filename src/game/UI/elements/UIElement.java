package game.UI.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.UI.UI;
import game.UI.UISorter;
import game.UI.elements.containers.UIContainer;

/**
 * 
 * 
 * @author Julius Häger
 */
public abstract class UIElement{

	// TODO: UIElement

	// JAVADOC: UIElement
	
	protected UI root;
	
	protected UIContainer parent;

	protected Rectangle area;

	protected int zOrder = 10;

	public UIElement() {
		area = new Rectangle();
	}

	public UIElement(Rectangle area) {
		this.area = area;
	}

	public UIElement(int width, int height) {
		area = new Rectangle(width, height);
	}
	
	public UIElement(int x, int y, int width, int height){
		area = new Rectangle(x, y, width, height);
	}
	
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
	
	public UI getRoot(){
		return root;
	}
	
	public void setZOrder(int z){
		zOrder = z;
	}

	public int getZOrder() {
		return zOrder;
	}
	
	public Rectangle getArea(){
		return area;
	}
	
	public Rectangle getBounds() {
		Rectangle parentArea = parent.getBounds();
		return new Rectangle(area.x + parentArea.x, area.y + parentArea.y, area.width, area.height);
	}
	
	public int getX(){
		return area.x;
	}
	
	public void setX(int x){
		area.setLocation(x, (int) area.getY());
	}
	
	public int getY(){
		return area.y;
	}
	
	public void setY(int y){
		area.setLocation((int)area.getX(), y);
	}
	
	public void setPosition(int x, int y){
		area.setLocation(x, y);
	}
	
	public int getWidth(){
		return area.width;
	}
	
	public int getHeight(){
		return area.height;
	}
	
	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

}
