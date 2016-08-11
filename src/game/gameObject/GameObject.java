package game.gameObject;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import game.gameObject.transform.Transform;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface GameObject extends Comparable<GameObject> {

	// JAVADOC: GameObject
	
	//FIXME: Find a way to optimize the default behaviour of the gameObject
	
	//NOTE: Should the whole gameEngine move to a component based design to make the implementation of standard behavior easier.
	// Should this change how different components behave? One thing that could be done is make the Component constructor private (like Unity)
	// But would that really benefit the GameEngine in any way? 
	// Having a component based would make some interesting questions more relevant, like:
	//
	// Should all gameObject have a transform? 
	// Should all transforms have a width and height? (Effectively: Should all transforms be BoxTransforms?)
	// 
	
	/**
	 * 
	 * @return
	 */
	public Transform getTransform();
	
	/**
	 * @return
	 */
	default public float getWidth(){
		return (float) (getBounds().getWidth() * getTransform().getScaleX());
	}
	
	/**
	 * @return
	 */
	default public float getHeight() {
		return (float) (getBounds().getHeight() * getTransform().getScaleY());
	}
	
	/**
	 * 
	 * @return
	 */
	public Shape getShape();
	
	/**
	 * @return
	 */
	default Shape getTranformedShape(){
		return getTransform().getAffineTransform().createTransformedShape(getShape());
	}
	
	/**
	 * Returns the GameObject bounds. Used to determine if the GameObjects
	 * methods should be called or not.
	 * 
	 * @return the GameObject bounds
	 */
	default public Rectangle2D getBounds(){
		return getTranformedShape().getBounds2D();//getTranformedShape().getBounds2D();
	}

	/**
	 * <p>Returns whether or not the GameObject is active.</p>
	 * <p>This determines if some subsystems should include the GameObject. 
	 * Notable examples are painting and collision detection</p>
	 * 
	 * @return Whether or not the GameObject is active.
	 */
	public boolean isActive();
	
	/**
	 * <p>Sets the GameObjects active state.</p>
	 * <p>The active state determines if some subsystems should include the GameObject. 
	 * Notable examples are painting and collision detection</p>
	 * 
	 * @param active
	 */
	public void setActive(boolean active);

	/**
	 * Returns the current Z-Order of the GameObject.
	 * 
	 * @return the current Z-Order of the GameObject.
	 */
	public int getZOrder();

	/**
	 * Used to compare the Z-Order values of two GameObjects.
	 */
	@Override
	default public int compareTo(GameObject object){
		return getZOrder() - object.getZOrder();
	}
}
