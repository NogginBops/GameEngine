package game.gameObject.graphics;

import game.Game;
import game.gameObject.GameObject;
import game.gameObject.physics.Movable;
import game.input.keys.KeyListener;
import game.util.GameObjectHandler;
import game.util.UpdateListener;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * A Camera is the object responsible for looking into a {@link Game Games}
 * scene and painting what it sees.
 * 
 * @version 1.0
 * @author Julius H�ger
 */
public class Camera extends Painter implements Movable, UpdateListener, KeyListener {

	// TODO: Remove movement code

	// TODO: Fix gameObjectHandeler in constructor

	private GameObjectHandler gameObjectHandler;

	private float x;
	private float y;

	private int width;
	private int height;

	private float dx;
	private float dy;

	private int ZOrder = Integer.MAX_VALUE - 8;

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
	public Camera(GameObjectHandler gameObjectHandeler, int x, int y, int width, int height) {
		this.gameObjectHandler = gameObjectHandeler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the current height of the camera viewport.
	 * 
	 * @return The height of the camera viewport.
	 */
	public int getHeight() {
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
	}
	
	/**
	 * Sets the height of the camera.
	 * 
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
		updateBounds();
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
	}

	@Override
	public void update(long timeNano) {
		x += (dx * timeNano) / 1000000000;
		y += (dy * timeNano) / 1000000000;
		updateBounds();
		
		//This update is synced with the gameobjecthandler
		if (gameObjectHandler.shouldUpdateObjects()) {
			paintables = gameObjectHandler.getAllGameObjectsExtending(Paintable.class);
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
	 */
	@Override
	public void paint(Graphics2D g2d) {
		
		g2d.setBackground(backgroundColor);
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, width, height);
		
		//This thread is not synced with gameobjecthandler and such should not update them here
		
		/*if (gameObjectHandler.shouldUpdateObjects()) {
			paintables = gameObjectHandler.getAllGameObjectsExtending(Paintable.class);
			
			System.out.println("Updated paintables");
		}*/
		
		super.paint(g2d);
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
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * The cameras bounds are used to determine what objects to paint.
	 * </p>
	 */
	@Override
	public Rectangle getBounds() {
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
		return ZOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if (ZOrder == object.getZOrder()) {
			return 0;
		} else {
			return ZOrder > object.getZOrder() ? 1 : -1;
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
