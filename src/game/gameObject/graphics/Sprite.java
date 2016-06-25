package game.gameObject.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Game;
import game.gameObject.graphics.animation.Animation;
import game.gameObject.physics.BasicMovable;
import game.image.effects.ColorTintFilter;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Sprite extends BasicMovable implements Paintable {

	// JAVADOC: Sprite
	
	/**
	 * The sprite image of the sprite.
	 */
	private BufferedImage sprite = null;
	
	/**
	 * The color of the sprite.
	 * If the sprite image is null the sprite will be a rectangle with that color
	 * else the color will tint the image, white will do nothing.
	 */
	private Color color = Color.WHITE;
	
	//NOTE: Should this system be implemented or should there a another more flexible way to do it? (AnimationManager?)
	
	/**
	 * The animation of the sprite if any
	 */
	private Animation animation = null;
	
	private BufferedImage graphicsReadySprite = null;
	
	private ColorTintFilter colorTinter;
	
	//NOTE: Is this creating a lot of unused sprites? Should there be a way to disable is feature?
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
		super(x, y, width, height, 5);
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
	public Sprite(float x, float y, float scale, BufferedImage sprite){
		super(x, y, sprite.getWidth(), sprite.getHeight(), 5);
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
	 * 			  the image of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, BufferedImage sprite) {
		super(x, y, width, height, 5);
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
	 * 			  the color of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, Color color) {
		super(x, y, width, height, 5);
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
	 * 			  the image of the Sprite
	 * @param color 
	 * 			  the color of the Sprite
	 */
	public Sprite(float x, float y, float width, float height, BufferedImage sprite, Color color) {
		super(x, y, width, height, 5);
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
	//FIXME: Temporary constructor!!
	public Sprite(float x, float y, float width, float height, Animation animation) {
		super(x, y, width, height, 5);
		imageCache = new HashMap<>();
		
		this.animation = animation;
		
		setColor(color);
		setSprite(animation.getCurrentImage());
	}
	
	//TODO: Add sorting layers for sprites and such
	
	/**
	 * @param bounds
	 */
	public Sprite(Rectangle2D.Float bounds) {
		super(bounds, 5);
		imageCache = new HashMap<>();
		
		setColor(color);
		setSprite(null);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		if(sprite == null){
			g2d.setColor(color);
			g2d.fill(bounds);
		}else{
			g2d.drawImage(graphicsReadySprite, (int)x, (int)y, (int)width, (int)height, null);
		}
	}
	
	@Override
	public BufferedImage getImage() {
		return graphicsReadySprite;
	}

	/**
	 * <p>
	 * Updates the bounds of the Sprite.
	 * </p>
	 * 
	 * <p>
	 * The bounds are used to determine if the object is drawn.
	 * </p>
	 */
	@Override
	public void updateBounds() {
		bounds.x = (int) x;
		bounds.y = (int) y;
		bounds.width = width;
		bounds.height = height;
		//bounds = new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void update(float deltaTime) {
		x += dx * deltaTime;
		y += dy * deltaTime;
		updateBounds();
		
		//TODO: Animation!
		if(animation != null){
			animation.update(deltaTime);
			setSprite(animation.getCurrentImage());
		}
	}
	
	/**
	 * @param scale
	 */
	public void setScale(float scale){
		this.scale = scale;
		if(sprite != null){
			width = (int)(sprite.getWidth() * scale);
			height = (int)(sprite.getHeight() * scale);
			updateBounds();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public float getScale(){
		return scale;
	}
	
	/**
	 * @param animation
	 */
	public void setAnimation(Animation animation){
		this.animation = animation;
		preloadSprites(animation.getImages());
	}
	
	/**
	 * @param sprite
	 */
	public void setSprite(BufferedImage sprite){
		if(sprite == null){
			
			this.sprite = null;
			
		} else {
			
			this.sprite = sprite;
			graphicsReadySprite = getGraphicsReadySprite(sprite);
			
		}
	}
	
	/**
	 * @param sprites
	 */
	public void preloadSprites(BufferedImage ... sprites){
		Game.log.logMessage("Preloading " + sprites.length + " sprites.", "Sprite", "Optimization");
		for (int i = 0; i < sprites.length; i++) {
			if(!imageCache.containsKey(sprites[i])){
				imageCache.put(sprites[i], createGraphicsReadySprite(sprites[i]));
			}
		}
	}
	
	/**
	 * @return
	 */
	public BufferedImage getSprite(){
		return sprite;
	}
	
	/**
	 * @param color
	 */
	public void setColor(Color color){
		this.color = color;
		colorTinter = createColorFilter();
		tintCachedSprites();
		graphicsReadySprite = getGraphicsReadySprite(sprite);
	}
	
	/**
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	//TODO: Clean up, rename and optimize these methods
	
	private ColorTintFilter createColorFilter(){
		return new ColorTintFilter(color, 1f);
	}
	
	private void tintCachedSprites(){
		for (BufferedImage cachedSprite : imageCache.keySet()) {
			imageCache.put(cachedSprite, createGraphicsReadySprite(cachedSprite));
		}
	}
	
	private BufferedImage getGraphicsReadySprite(BufferedImage sprite){
		if(sprite == null){
			return null;
		}
		if(!imageCache.containsKey(sprite)){
			Game.log.logDebug("No image cached, creating a new image!", "Sprite", "Image");
			imageCache.put(sprite, createGraphicsReadySprite(sprite));
		}
		return imageCache.get(sprite);
	}
	
	private BufferedImage createGraphicsReadySprite(BufferedImage sprite){
		if(sprite != null){
			return colorTinter.filter(sprite, null);
		} else {
			Game.log.logError("Tried to create a graphics ready image from a null image!", new String[]{ "Sprite", "Image", "Graphics ready" });
			return null;
		}
	}
}
