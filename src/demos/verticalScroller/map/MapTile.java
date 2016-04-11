package demos.verticalScroller.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Sprite;

/**
 * @author Julius Häger
 *
 */
public class MapTile extends Sprite {
	
	//JAVADOC: MapTile
	
	private String name;

	private BufferedImage tileImage;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image 
	 */
	public MapTile(String name, float x, float y, int width, int height, int zOrder, BufferedImage image) {
		super(x, y, width, height);
		this.zOrder = zOrder;
		tileImage = image;
		this.name = name;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(tileImage, (int)x, (int)y, width, height, null);
	}
}
