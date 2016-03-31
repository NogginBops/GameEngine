package game.UI.elements.input;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import game.UI.elements.UIElement;
import game.gameObject.GameObject;
import game.input.mouse.MouseListener;

/**
 * @author Julius Häger
 *
 */
public class UIButton extends UIElement implements MouseListener {
	
	protected enum ButtonState{
		INACTIVE,
		IDLE,
		HOVER,
		ACTIVE;
	}
	
	protected ButtonState state = ButtonState.IDLE;
	
	protected Color color = Color.BLACK;
	
	/**
	 * @param x 
	 * @param y 
	 * @param width
	 * @param height
	 */
	public UIButton(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public UIButton(int width, int height) {
		super(width, height);
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
	public void updateBounds() {
		
	}

	@Override
	public int compareTo(GameObject object) {
		return 0;
	}
}
