package game.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Paintable;

/**
 * @author Julius Häger
 *
 */
public class OtherPaintable extends BasicGameObject implements Paintable {
	
	//Remove

	private Color color;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param z
	 * @param color
	 */
	public OtherPaintable(int x, int y, int width, int height, int z, Color color) {
		super(x, y, width, height, z);
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
