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
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
import game.gameObject.physics.Movable;
import game.gameObject.transform.BoxTransform;
import game.input.keys.KeyListener;
import game.screen.ScreenRect;
import game.util.image.ImageUtils;

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
	
	private BoxTransform<GameObject> boxTransform;
	
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
		
		transform = boxTransform = new BoxTransform<GameObject>(this, x, y, width, height);
		
		addEventListeners();
	}
	
	
	/**
	 * @param rect
	 * @param screenRect
	 * @param bgColor
	 */
	public Camera(Rectangle2D.Float rect, ScreenRect screenRect, Color bgColor) {
		super(rect.x, rect.y, rect.width, rect.height, Integer.MAX_VALUE - 8);
		
		transform = boxTransform = new BoxTransform<GameObject>(this, rect.x, rect.y, rect.width, rect.height);
		
		setScreenRectangle(screenRect);
		
		setBackgroundColor(bgColor);
		
		addEventListeners();
	}
	
	private void addEventListeners(){
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, (event) -> { if (event.object instanceof Paintable) paintables.add((Paintable)event.object); paintables.sort(null); });
		
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, (event) -> { if (event.object instanceof Paintable) paintables.remove((Paintable)event.object); paintables.sort(null); });
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
		return transform.getX();
	}


	@Override
	public float getY() {
		return transform.getY();
	}


	@Override
	public void setX(float x) {
		transform.setX(x);
	}


	@Override
	public void setY(float y) {
		transform.setY(y);
	}


	@Override
	public void setPosition(float x, float y) {
		transform.setPosition(x, y);
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
	
	@Override
	public void setVelocity(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Sets the width of the camera.
	 * 
	 * @param width
	 */
	public void setWidth(int width){
		boxTransform.setWidth(width);
		
		image = new BufferedImage((int)boxTransform.getWidth(), (int)boxTransform.getHeight(), BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
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
		boxTransform.setHeight(height);
		
		image = new BufferedImage((int)boxTransform.getWidth(), (int)boxTransform.getHeight(), BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
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
		this.shape = new Rectangle2D.Float(0, 0, width, height);
		boxTransform.setSize(width, height);
		
		//TODO: Synchronize?
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
		if(translatedGraphics != null){
			translatedGraphics.dispose();
			translatedGraphics = image.createGraphics();
			originalTransform = translatedGraphics.getTransform();
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
		g2d.fillRect(0, 0, (int)boxTransform.getWidth(), (int)boxTransform.getHeight());
		
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
		translatedGraphics.fillRect(0, 0, (int)boxTransform.getWidth(), (int)boxTransform.getHeight());
		translatedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		return super.getImage();
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
		
		if(Game.keyHandler.isBound("Left", e.getKeyCode())){
			moveLeft = true;
		}
		
		if(Game.keyHandler.isBound("Right", e.getKeyCode())){
			moveRight = true;
		}
		
		if(Game.keyHandler.isBound("Up", e.getKeyCode())){
			moveUp = true;
		}
		
		if(Game.keyHandler.isBound("Down", e.getKeyCode())){
			moveDown = true;
		}
		
		updateMovement();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(Game.keyHandler.isBound("Left", e.getKeyCode())){
			moveLeft = false;
		}
		
		if(Game.keyHandler.isBound("Right", e.getKeyCode())){
			moveRight = false;
		}
		
		if(Game.keyHandler.isBound("Up", e.getKeyCode())){
			moveUp = false;
		}
		
		if(Game.keyHandler.isBound("Down", e.getKeyCode())){
			moveDown = false;
		}
		
		updateMovement();
	}

	private void updateMovement() {
		float dx = 0;
		dx += moveLeft ? -cameraMovementSpeed : 0;
		dx += moveRight ? cameraMovementSpeed : 0;
		
		float dy = 0;
		dy += moveUp ? -cameraMovementSpeed : 0;
		dy += moveDown ? cameraMovementSpeed : 0;
		
		dx = (float) ((dx * Math.cos(transform.getRotationRad())) - (dy * Math.sin(transform.getRotationRad())));
		
		dy = (float) ((dy * Math.cos(transform.getRotationRad())) + (dx * Math.sin(transform.getRotationRad())));
		
		setDX(dx);
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
