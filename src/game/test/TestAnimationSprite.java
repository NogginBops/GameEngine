package game.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Sprite;

/**
 * @author Julius Häger
 *
 */
public class TestAnimationSprite extends Sprite{
	
	private BufferedImage[] images;
	
	private float delay;
	
	private float timer;
	
	private int current;
	
	private boolean reverse = false;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param delay
	 * @param images
	 */
	public TestAnimationSprite(float x, float y, int width, int height, float delay, BufferedImage ... images) {
		super(x, y, width, height);
		this.delay = delay;
		this.images = images;
		timer = 0;
		current = 0;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(images[current], (int)x, (int)y, width, height, null);
	}
	int temp = 0;
	@Override
	public void update(long timeMillis) {
		super.update(timeMillis);
		timer += timeMillis / 1000000000f;
		if(timer > delay){
			temp = current;
			temp += reverse ? -1 : 1;
			if(temp < 0){
				temp = 1;
				reverse = false;
			}else if(temp >= images.length){
				temp = images.length - 2;
				reverse = true;
			}
			current = temp;
			timer = 0;
		}
	}
}
