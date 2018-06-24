package game.test.updaterTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Paintable;
import game.gameObject.physics.BasicCollidable;
import game.util.math.ColorUtils;

/**
 * @author Julius Häger
 *
 */
public class BasicTimeEffector extends BasicCollidable implements TimeEffector, Paintable{

	protected float timeMultiplier = 1;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 * @param shape
	 */
	public BasicTimeEffector(float x, float y, float width, float height, int zOrder) {
		super(x, y, width, height, zOrder, new Rectangle2D.Float(x, y , width, height));
	}

	/**
	 * @param x 
	 * @param y 
	 * @param rect
	 * @param zOrder
	 * @param shape
	 */
	public BasicTimeEffector(float x, float y, Shape shape, int zOrder) {
		super(x, y, shape, zOrder);
	}
	
	Color color = ColorUtils.createTransparent(Color.magenta, 0.2f);
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(getCollitionShape());
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	@Override
	public float getTimeMultiplier() {
		return timeMultiplier;
	}

	/**
	 * @param timeMultiplier
	 */
	public void setTimeMultiplier(float timeMultiplier) {
		this.timeMultiplier = timeMultiplier;
		if(timeMultiplier == 0){
			color = ColorUtils.createTransparent(Color.yellow, 0.7f);
		}else if(timeMultiplier < 1){
			color = ColorUtils.createTransparent(Color.magenta, 1 - timeMultiplier);
		}else if(timeMultiplier > 1){
			color = ColorUtils.createTransparent(Color.cyan, 1/(50/timeMultiplier));
		}else{
			color = ColorUtils.createTransparent(Color.black, 0);
		}
	}
}
