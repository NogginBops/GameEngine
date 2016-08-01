package game.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gameObject.graphics.Sprite;

/**
 * @author Julius Häger
 *
 */
public class TestAnimationSprite extends Sprite{
	
	/* FIXME: Handle this with sprite tinting. 
	 * This could be done with some trickery with pre-tinting frames.
	 * That solution would mean that the graphicsReadySprite would have to be visible to children. :/
	 * 
	 */
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
		g2d.drawImage(images[current], 0, 0, (int)getWidth(), (int)getHeight(), null);
	}
	
	int temp = 0;
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		timer += deltaTime;
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
