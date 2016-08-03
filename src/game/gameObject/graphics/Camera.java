package game.gameObject.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;
import game.gameObject.handler.event.GameObjectEvent;
import game.gameObject.physics.Movable;
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
public class Camera extends Painter implements Movable, KeyListener, EventListener {

	// TODO: Remove movement code
	
	//TODO: Implement a way to zoom
	
	private float width;
	private float height;
	
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
		
		this.width = width;
		this.height = height;
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, this);
	}
	
	
	/**
	 * @param rect
	 * @param screenRect
	 * @param bgColor
	 */
	public Camera(Rectangle2D.Float rect, ScreenRect screenRect, Color bgColor) {
		super(rect.x, rect.y, rect.width, rect.height, Integer.MAX_VALUE - 8);
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, this);
		
		setScreenRectangle(screenRect);
		
		setBackgroundColor(bgColor);
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
		return transform.getY();
	}


	@Override
	public float getY() {
		return transform.getX();
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
		this.width = width;
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
		translatedGraphics.dispose();
		translatedGraphics = image.createGraphics();
	}
	
	/**
	 * Sets the height of the camera.
	 * 
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
		translatedGraphics.dispose();
		translatedGraphics = image.createGraphics();
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
		
		//TODO: Synchronize?
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemOptimizedImage(image);
		if(translatedGraphics != null){
			translatedGraphics.dispose();
			translatedGraphics = image.createGraphics();
		}
	}

	@Override
	public void update(float deltaTime) {
		transform.translate(dx * deltaTime, dy * deltaTime);
		
		//This update is synced with the gameobjecthandler
		//if (Game.gameObjectHandler.shouldUpdateObjects()) {
		//	paintables = Game.gameObjectHandler.getAllGameObjectsExtending(Paintable.class);
		//}
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


	@Override
	public <T extends GameEvent<?>> void eventFired(T event) {
		if(event instanceof GameObjectEvent){
			GameObjectEvent goEvent = (GameObjectEvent) event;
			if(goEvent.object instanceof Paintable){
				switch (event.command) {
				case "Created":
					paintables.add((Paintable) goEvent.object);
					paintables.sort(null);
					break;
				case "Destroyed":
					paintables.remove((Paintable) goEvent.object);
					paintables.sort(null);
					break;
				default:
					break;
				}
			}
		}
	}
}
