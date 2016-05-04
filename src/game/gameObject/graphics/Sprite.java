package game.gameObject.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Game;
import game.gameObject.BasicGameObject;
import game.gameObject.GameObject;
import game.gameObject.physics.Movable;
import game.image.effects.ColorTintFilter;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Sprite extends BasicGameObject implements Paintable, Movable {

	// JAVADOC: Sprite
	
	/**
	 * The dynamic-x (The movement in the x-axis measured in pixels/second)
	 */
	protected float dx;
	
	/**
	 * The dynamic-x (The movement in the y-axis measured in pixels/second)
	 */
	protected float dy;
	
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
	
	private BufferedImage graphicsReadySprite = null;
	
	private ColorTintFilter colorTinter;
	
	private HashMap<BufferedImage, BufferedImage> imageCache;

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
	public Sprite(float x, float y, int width, int height) {
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
	 * @param sprite 
	 * 			  the image of the Sprite
	 */
	public Sprite(float x, float y, int width, int height, BufferedImage sprite) {
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
	public Sprite(float x, float y, int width, int height, Color color) {
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
	public Sprite(float x, float y, int width, int height, BufferedImage sprite, Color color) {
		super(x, y, width, height, 5);
		imageCache = new HashMap<>();
		
		setColor(color);
		setSprite(sprite);
	}
	
	//TODO: Add sorting layers for sprites and such
	
	/**
	 * @param bounds
	 */
	public Sprite(Rectangle bounds) {
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
			g2d.drawImage(graphicsReadySprite, (int)x, (int)y, width, height, null);
		}
	}
	
	@Override
	public BufferedImage getImage() {
		return sprite;
	}

	@Override
	public int getZOrder() {
		return zOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		return zOrder - object.getZOrder();
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
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
	public void update(long timeNano) {
		x += (dx * timeNano) / 1000000000;
		y += (dy * timeNano) / 1000000000;
		updateBounds();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets the location of the sprite.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void setLocation(float x, float y){
		this.x = x;
		this.y = y;
	}

	@Override
	public float getDX() {
		return dx;
	}

	@Override
	public float getDY() {
		return dy;
	}

	@Override
	public void setDX(float dx) {
		this.dx = dx;
	}

	@Override
	public void setDY(float dy) {
		this.dy = dy;
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
		createColorFilter();
		//imageCache = new HashMap<>();
		//graphicsReadySprite = getGraphicsReadySprite(sprite);
	}
	
	/**
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	private void createColorFilter(){
		colorTinter = new ColorTintFilter(color, 1f);
	}
	
	private BufferedImage getGraphicsReadySprite(BufferedImage sprite){
		if(!imageCache.containsKey(sprite)){
			Game.log.logMessage("No image cached, creating a new image!");
			imageCache.put(sprite, createGraphicsReadySprite(sprite));
		}
		return imageCache.get(sprite);
	}
	
	private BufferedImage createGraphicsReadySprite(BufferedImage sprite){
		if(sprite != null){
			Game.log.logMessage("CreatingGraphicsReadySprite!");
			return colorTinter.filter(sprite, null);
		} else {
			Game.log.logError("Tried to create a graphics ready image from a null image!", new String[]{ "Sprite", "Image", "Graphics ready" });
			return null;
		}
	}
}
