package game.UI.elements.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class UILabel extends UIElement {
	
	//TODO: Manage to get width and height of the label
	
	//JAVADOC: UILable

	protected String text;

	protected Color color = Color.WHITE;
	
	protected Font font = Font.getFont(Font.SANS_SERIF);

	private FontMetrics fontMetrics;
	
	/**
	 * @param text
	 */
	public UILabel(String text) {
		super();
		this.text = text;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param text
	 */
	public UILabel(float x, float y, String text) {
		super(x, y, 0, 0);
		this.text = text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * @param font
	 */
	public void setFont(Font font){
		this.font = font;
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2d) {
		Font temp = g2d.getFont();
		
		//TODO: Find a more global way to handle AA
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//TODO: Handle this more elegantly and in a more useful way
		g2d.setFont(font);
		fontMetrics = g2d.getFontMetrics();
		setWidth(fontMetrics.stringWidth(text));
		setHeight(fontMetrics.getHeight());
		
		g2d.setColor(color);
		
		g2d.drawString(text, (int)area.getX(), (int)area.getY() + fontMetrics.getHeight());
		
		g2d.setFont(temp);
	}
	
	/**
	 * @param g2d
	 * @return
	 */
	public FontMetrics getFontMetrics(Graphics2D g2d){
		g2d.setFont(font);
		return g2d.getFontMetrics();
	}
}
