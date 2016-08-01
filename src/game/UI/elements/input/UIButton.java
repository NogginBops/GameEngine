package game.UI.elements.input;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.Action;

import game.UI.elements.UIElement;
import game.gameObject.GameObject;
import game.gameObject.transform.Transform;
import game.input.mouse.MouseListener;

/**
 * @author Julius Häger
 *
 */
public class UIButton extends UIElement implements MouseListener {
	
	//JAVADOC: UIButton
	
	//TODO: UI active state!
	
	private boolean active;
	
	/**
	 * 
	 * @author Julius Häger
	 */
	protected enum ButtonState{
		/**
		 * 
		 */
		INACTIVE,
		/**
		 * 
		 */
		IDLE,
		/**
		 * 
		 */
		HOVER,
		/**
		 * 
		 */
		ACTIVE;
	}
	
	/**
	 * 
	 */
	protected ButtonState state = ButtonState.IDLE;
	
	/**
	 * 
	 */
	protected Color color = Color.BLACK;
	
	protected ArrayList<ActionListener> listeners;
	
	protected Action event;
	
	/**
	 * @param x 
	 * @param y 
	 * @param width
	 * @param height
	 */
	public UIButton(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		
		
		//listeners.get(0).actionPerformed(new ActionEvent(this, 1, ""));
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public UIButton(float width, float height) {
		super(width, height);
	}
	
	@Override
	public Shape getShape() {
		return area;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int compareTo(GameObject object) {
		return 0;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		
		switch (state) {
		case INACTIVE:
			color = Color.GRAY;
			break;
		case IDLE:
			color = Color.LIGHT_GRAY;
			break;
		case HOVER:
			color = Color.GREEN;
			break;
		case ACTIVE:
			color = Color.RED;
			break;
		default:
			color = Color.MAGENTA;
			break;
		}
		
		g2d.setColor(color);
		
		g2d.fill(area);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(state != ButtonState.ACTIVE){
			state = ButtonState.ACTIVE;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(state == ButtonState.ACTIVE){
			state = ButtonState.HOVER;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(state != ButtonState.INACTIVE){
			state = ButtonState.HOVER;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(state == ButtonState.HOVER){
			state = ButtonState.IDLE;
		}else if(state == ButtonState.ACTIVE){
			//TODO: UIButton:  Active until release
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseWeelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public boolean absorb() {
		return true;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return false;
	}

	@Override
	public Transform getTransform() {
		return null;
	}
}
