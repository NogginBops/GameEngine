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
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image 
	 */
	public MapTile(String name, float x, float y, int width, int height, int zOrder, BufferedImage sprite) {
		super(x, y, width, height);
		this.zOrder = zOrder;
		this.name = name;
		setSprite(sprite);
	}
}
