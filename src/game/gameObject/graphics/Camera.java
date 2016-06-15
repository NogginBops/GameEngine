package game.gameObject.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;
import game.gameObject.physics.Movable;
import game.input.keys.KeyListener;
import game.screen.ScreenRect;

/**
 * A Camera is the object responsible for looking into a {@link Game Games}
 * scene and painting what it sees.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Camera extends Painter implements Movable, KeyListener {

	// TODO: Remove movement code
	
	//TODO: Implement a way to zoom
	
	private float dx;
	private float dy;
	
	private boolean shouldReceiveKeyboardInput = false;

	private int cameraMovementSpeed = 200;

	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean moveDown;

	private Color backgroundColor = Color.BLACK;

	/**
	 * Creates a instance of a Camera object with the specified location, width
	 * and height.
	 * 
	 * @param gameObjectHandeler
	 *            - The {@link GameObjectHandler} whose content the camera
	 *            should render.
	 * @param x
	 *            - The start x-coordinate of the upper most corner of the
	 *            camera.
	 * @param y
	 *            - The start x-coordinate of the upper most corner of the
	 *            camera.
	 * @param width
	 *            - The width of the Camera viewport (in pixels).
	 * @param height
	 *            - The height of the Camera viewport (in pixels).
	 */
	public Camera(float x, float y, float width, float height) {
		super(x, y, width, height, Integer.MAX_VALUE - 8);
		
		updateBounds();
	}
	
	
	/**
	 * @param rect
	 * @param screenRect
	 * @param bgColor
	 */
	public Camera(Rectangle2D.Float rect, ScreenRect screenRect, Color bgColor) {
		super(rect.x, rect.y, rect.width, rect.height, Integer.MAX_VALUE - 8);
		
		setScreenRectangle(screenRect);
		
		setBackgroundColor(bgColor);
		
		updateBounds();
	}

	/**
	 * Used to set whether or not the camera should receive keyboard input.
	 * 
	 * @param bool
	 *            - Whether or not the camera should receive keyboard input.
	 */
	public void receiveKeyboardInput(boolean bool) {
		shouldReceiveKeyboardInput = bool;
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
		updateBounds();
	}

	@Override
	public void setY(float y) {
		this.y = y;
		updateBounds();
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		updateBounds();
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
	 * Returns the current width of the camera viewport.
	 * 
	 * @return The width of the camera viewport.
	 */
	@Override
	public float getWidth() {
		return width;
	}

	/**
	 * Returns the current height of the camera viewport.
	 * 
	 * @return The height of the camera viewport.
	 */
	@Override
	public float getHeight() {
		return height;
	}
	
	/**
	 * Sets the width of the camera.
	 * 
	 * @param width
	 */
	public void setWidth(int width){
		this.width = width;
		updateBounds();
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		translatedGraphics.dispose();
		translatedGraphics = image.createGraphics();
		originalTransform = translatedGraphics.getTransform();
	}
	
	/**
	 * Sets the height of the camera.
	 * 
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
		updateBounds();
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		translatedGraphics.dispose();
		translatedGraphics = image.createGraphics();
		originalTransform = translatedGraphics.getTransform();
	}
	
	/**
	 * Sets the width and height of the camera.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		updateBounds();
		
		//TODO: Synchronize?
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(translatedGraphics != null){
			translatedGraphics.dispose();
			translatedGraphics = image.createGraphics();
			originalTransform = translatedGraphics.getTransform();
		}
	}

	@Override
	public void update(float deltaTime) {
		x += dx * deltaTime;
		y += dy * deltaTime;
		updateBounds();
		
		//This update is synced with the gameobjecthandler
		if (Game.gameObjectHandler.shouldUpdateObjects()) {
			paintables = Game.gameObjectHandler.getAllGameObjectsExtending(Paintable.class);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This methods only updates {@link #paintables} array when
	 * {@link GameObjectHandler#shouldUpdateObjects()} method
	 * returns true.
	 * </p>
	 * @deprecated
	 */
	@Override
	public void paint(Graphics2D g2d) {
		
		g2d.setBackground(backgroundColor);
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, (int)width, (int)height);
		
		super.paint(g2d);
	}
	
	//Graphics2D g2d;
	
	@Override
	public BufferedImage getImage() {
		if(translatedGraphics == null){
			translatedGraphics = image.createGraphics();
			originalTransform = translatedGraphics.getTransform();
		}
		
		//NOTE: Should this be done in the painter?
		
		translatedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		translatedGraphics.setColor(backgroundColor);
		translatedGraphics.fillRect(0, 0, (int)width, (int)height);
		translatedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		return super.getImage();
	}

	/**
	 * <p>
	 * Updates the bounds of the camera.
	 * </p>
	 * 
	 * <p>
	 * The bounds are used to determine what objects to draw.
	 * </p>
	 */
	@Override
	public void updateBounds() {
		bounds.x = (int) x;
		bounds.y = (int) y;
		bounds.width = width;
		bounds.height = height;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * The cameras bounds are used to determine what objects to paint.
	 * </p>
	 */
	@Override
	public Rectangle2D.Float getBounds() {
		return bounds;
	}

	/**
	 * <p>
	 * A camera object is always at the top of the scene and thus has a value of
	 * <code>Integer.MAX_VALUE - 8</code>.
	 * </p>
	 * <p>
	 * The z-level is not <code>Integer.MAX_VALUE</code> but 8 less to leave
	 * room for other functionality;
	 */
	@Override
	public int getZOrder() {
		return zOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if (zOrder == object.getZOrder()) {
			return 0;
		} else {
			return zOrder > object.getZOrder() ? 1 : -1;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown = true;
		}
		updateMovement();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown = false;
		}
		updateMovement();
	}

	private void updateMovement() {
		int dx = 0;
		dx += moveLeft ? -cameraMovementSpeed : 0;
		dx += moveRight ? cameraMovementSpeed : 0;
		setDX(dx);
		int dy = 0;
		dy += moveUp ? -cameraMovementSpeed : 0;
		dy += moveDown ? cameraMovementSpeed : 0;
		setDY(dy);
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return shouldReceiveKeyboardInput;
	}

	/**
	 * Sets the background color of the camera.
	 * 
	 * @param color
	 *            - The new background color.
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}
}
