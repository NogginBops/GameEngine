package game.gameObject.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Game;
import game.gameObject.graphics.animation.Animation;
import game.gameObject.physics.BasicRotatable;
import game.image.effects.ColorTintFilter;

/**
 * 
 * 
 * @version 1.0
 * @author Julius H�ger
 */
public class Sprite extends BasicRotatable implements Paintable {

	// JAVADOC: Sprite
	
	//TODO: A flag to disable all of the sprites systems so that it basically behaves like a moving picture (no tint color, no graphicsReadySprite, no animation)
	// Scale should still work
	
	/**
	 * The sprite image of the sprite.
	 */
	private BufferedImage sprite = null;

	/**
	 * The color of the sprite. If the sprite image is null the sprite will be a
	 * rectangle with that color else the color will tint the image, white will
	 * do nothing.
	 */
	private Color color = Color.WHITE;

	// NOTE: Should this system be implemented or should there a another more
	// flexible way to do it? (AnimationManager?)

	/**
	 * The animation of the sprite if any
	 */
	private Animation animation = null; // NOTE: This will change!

	private BufferedImage graphicsReadySprite = null;

	private ColorTintFilter colorTinter;

	// NOTE: Is this creating a lot of unused sprites? Should there be a way to
	// disable is feature?
	private HashMap<BufferedImage, BufferedImage> imageCache;

	private float scale = 1;
	
	/**
	 * 
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param width
	 *            the width of the Sprite (in pixels)
	 * @param height
	 *            the height of the Sprite (in pixels)
	 */
	public Sprite(float x, float y, float width, float height) {
		super(x, y, width, height, 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(sprite);
	}

	/**
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param scale
	 * @param sprite
	 */
	public Sprite(float x, float y, float scale, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(sprite);
		setScale(scale);
	}

	/**
	 * 
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param width
	 *            the width of the Sprite (in pixels)
	 * @param height
	 *            the height of the Sprite (in pixels)
	 * @param sprite
	 *            the image of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, BufferedImage sprite) {
		super(x, y, width, height, 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(sprite);
	}

	/**
	 * 
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param width
	 *            the width of the Sprite (in pixels)
	 * @param height
	 *            the height of the Sprite (in pixels)
	 * @param color
	 *            the color of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, Color color) {
		super(x, y, width, height, 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(null);
	}

	/**
	 * 
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param width
	 *            the width of the Sprite (in pixels)
	 * @param height
	 *            the height of the Sprite (in pixels)
	 * @param sprite
	 *            the image of the Sprite
	 * @param color
	 *            the color of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, BufferedImage sprite, Color color) {
		super(x, y, width, height, 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(sprite);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param animation
	 */
	// FIXME: Temporary constructor!!
	public Sprite(float x, float y, float width, float height, Animation animation) {
		super(x, y, width, height, 5, 0);
		imageCache = new HashMap<>();

		this.animation = animation;

		setColor(color);
		setSprite(animation.getCurrentImage());
	}
	
	//NOTE: Should the param be named bounds? Shouldn't it be named something like 'shape'?

	/**
	 * @param x 
	 * @param y 
	 * @param bounds
	 */
	public Sprite(float x, float y, Rectangle2D bounds) {
		super(x, y, bounds, 5, 0);
		imageCache = new HashMap<>();

		setColor(color);
		setSprite(null);
	}

	//NOTE: Should the paint() graphics object be rotated?
	
	@Override
	public void paint(Graphics2D g2d) {
		if (sprite == null) {
			g2d.setColor(color);
			g2d.fill(getTranformedShape());
		} else {
			g2d.drawImage(graphicsReadySprite, (int) transform.getX(), (int) transform.getY(), (int) getBounds().getWidth(), (int) getBounds().getHeight(), null);
		}
	}

	@Override
	public BufferedImage getImage() {
		return graphicsReadySprite;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		//transform.translate(dx * deltaTime, dy * deltaTime);
		
		// TODO: Animation!
		if (animation != null) {
			animation.update(deltaTime);
			setSprite(animation.getCurrentImage());
		}
	}

	/**
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
		transform.setScale(scale, scale);
	}
	
	/**
	 * 
	 * @return
	 */
	public float getScale() {
		return scale;
	}
	
	@Override
	public float getWidth() {
		if(sprite != null){
			return sprite.getWidth() * scale;
		}
		return super.getWidth();
	}
	
	@Override
	public float getHeight() {
		if(sprite != null){
			return sprite.getHeight() * scale;
		}
		return super.getHeight();
	}

	/**
	 * @param animation
	 */
	public void setAnimation(Animation animation) {
		this.animation = animation;
		preloadSprites(animation.getImages());
	}
	
	/**
	 * @return
	 */
	public Animation getAnimation(){
		return animation;
	}

	/**
	 * @param sprite
	 */
	public void setSprite(BufferedImage sprite) {
		if (this.sprite == sprite) {
			return;
		}
		
		if (sprite == null) {
			this.sprite = null;
		} else {
			this.sprite = sprite;
			graphicsReadySprite = getGraphicsReadySprite(sprite);
		}
	}

	/**
	 * @param sprites
	 */
	public void preloadSprites(BufferedImage... sprites) {
		Game.log.logDebug("Preloading " + sprites.length + " sprites.", "Sprite", "Optimization");
		for (int i = 0; i < sprites.length; i++) {
			if (!imageCache.containsKey(sprites[i])) {
				imageCache.put(sprites[i], createGraphicsReadySprite(sprites[i]));
			}
		}
	}

	/**
	 * @return
	 */
	public BufferedImage getSprite() {
		return sprite;
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		colorTinter = createColorFilter();
		tintCachedSprites();
		graphicsReadySprite = getGraphicsReadySprite(sprite);
	}

	/**
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	// TODO: Clean up, rename and optimize these methods

	private ColorTintFilter createColorFilter() {
		return new ColorTintFilter(color, 1f);
	}

	private void tintCachedSprites() {
		for (BufferedImage cachedSprite : imageCache.keySet()) {
			imageCache.put(cachedSprite, createGraphicsReadySprite(cachedSprite));
		}
	}

	private BufferedImage getGraphicsReadySprite(BufferedImage sprite) {
		if (sprite == null) {
			return null;
		}
		if (!imageCache.containsKey(sprite)) {
			Game.log.logDebug("No image cached, creating a new image!", "Sprite", "Image");
			imageCache.put(sprite, createGraphicsReadySprite(sprite));
		}
		return imageCache.get(sprite);
	}

	private BufferedImage createGraphicsReadySprite(BufferedImage sprite) {
		if (sprite != null) {
			return colorTinter.filter(sprite, null);
		} else {
			Game.log.logError("Tried to create a graphics ready image from a null image!", "Sprite", "Image", "Graphics ready");
			
			return null;
		}
	}
	
	@Override
	public String[] getDebugValues() {
		String[] superValues = super.getDebugValues();
		String[] ownValues = new String[]{
				"<b>Sprite:</b> " + sprite,
				"<b>Color:</b> " + color,
				"<b>Animation:</b> " + animation,
				"<b>Scale:</b> " + scale,
		};
		
		String[] mergedValues = new String[superValues.length + ownValues.length];
		System.arraycopy(superValues, 0, mergedValues, 0, superValues.length);
		System.arraycopy(ownValues, 0, mergedValues, superValues.length, ownValues.length);
		return mergedValues;
	}
}
