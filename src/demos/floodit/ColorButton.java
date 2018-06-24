package demos.floodit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;
import game.input.mouse.MouseListener;
import game.util.Procedure;

/**
 * @author Julius Häger
 *
 */
public class ColorButton extends BasicGameObject implements Paintable, MouseListener {

	private Color color;
	
	private Procedure onClick;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color 
	 * @param onClick 
	 */
	public ColorButton(float x, float y, float width, float height, Color color, Procedure onClick) {
		super(x, y, width, height, 5);
		
		this.color = color;
		this.onClick = onClick;
	}
	
	@Override
	public BufferedImage getImage() {
		return null;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(getTranformedShape());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		onClick.proc();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public boolean absorb() {
		return false;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return false;
	}

	@Override
	public Rectangle2D getBounds() {
		return super.getBounds();
	}
}
