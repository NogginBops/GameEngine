package game.UI.elements.input;

import java.awt.Color;
import java.awt.Graphics2D;
<<<<<<< HEAD
import java.awt.Shape;
=======
import java.awt.event.ActionEvent;
>>>>>>> refs/remotes/origin/GameEngine(Nightly)
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

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
	
	private boolean active = true;
	
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
	
	protected ArrayList<ActionListener> listeners = new ArrayList<>();
	
	/**
	 * @param x 
	 * @param y 
	 * @param width
	 * @param height
	 */
	public UIButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public UIButton(float width, float height) {
		super(width, height);
	}
	
	/**
	 * @param listener
	 */
	public void addActionListener(ActionListener listener){
		listeners.add(listener);
	}
	
	/**
	 * @param listener
	 * @return
	 */
	public boolean removeActionListener(ActionListener listener){
		return listeners.remove(listener);
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
	public Float getBounds() {
		return super.getBounds();
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
		
		g2d.fill(getArea());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Clicked");
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_FIRST, "");
		for (ActionListener actionListener : listeners) {
			actionListener.actionPerformed(event);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("Pressed");
		if(state != ButtonState.ACTIVE){
			state = ButtonState.ACTIVE;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("Released");
		if(state == ButtonState.ACTIVE){
			state = ButtonState.HOVER;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("Entered");
		if(state != ButtonState.INACTIVE){
			state = ButtonState.HOVER;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("Exited");
		if(state == ButtonState.HOVER){
			state = ButtonState.IDLE;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("Dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("Moved");
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public boolean absorb() {
		return true;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		if(state == ButtonState.ACTIVE){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Transform getTransform() {
		return null;
	}
}
