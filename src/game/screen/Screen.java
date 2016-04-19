package game.screen;

import game.Game;
import game.debug.DebugOutputProvider;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Painter;
import game.input.Input;
import game.util.FPSCounter;
import game.util.UpdateCounter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * A screen object manages repainting of a window
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Screen implements Runnable {

	// JAVADOC: Screen
	
	//TODO: Merge with ScreenManager

	private boolean isRunning = false;

	private boolean debug = false;

	private Graphics2D g2d;

	private Painter painter;
	
	//TODO: Multiple cameras (Painters) with viewport rectangles
	
	//TODO: Render painters to buffered images and combine with respect to some kind of ScreenRect
	
	//TODO: Add support for image effects and filters
	
	//TODO: Support image effects for individual painters
	
	private ArrayList<Painter> painters;
	
	private ArrayList<BufferedImage> cameraImages;
	
	private ArrayList<DebugOutputProvider> debugPrintOuts;

	/**
	 * @param width
	 * @param height
	 * @param state
	 * @param title
	 */
	public Screen(int width, int height, int state, String title) {
		ScreenManager.createFrame(width, height, state, title);
		
		painters = new ArrayList<Painter>();
		cameraImages = new ArrayList<BufferedImage>();
		
		
		
		debugPrintOuts = new ArrayList<DebugOutputProvider>();
	}

	/**
	 * Sets up the screen using {@code setUpDisplay()} method then starts a
	 * repaint-loop
	 */
	public void start() {
		isRunning = true;
		loop();
	}

	/**
	 * Resets the screen to it's original settings.
	 */
	private void resetDisplay() {
		ScreenManager.closeFrame();
	}

	private void loop() {
		long currentTime = System.nanoTime();
		long elapsedTime = 0;
		while (isRunning) {
			elapsedTime = System.nanoTime() - currentTime;
			currentTime = System.nanoTime();
			
			g2d = ScreenManager.getGraphics();
			g2d.clearRect(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
			
			if (painter != null) {
				painter.paint(g2d);
			}
			
			if (debug) {
				g2d.setColor(Color.GREEN.brighter());
				for (int i = 0; i < debugPrintOuts.size(); i++) {
					for (String debugOutput : debugPrintOuts.get(i).GetDebugValues()) {
						i++;
						g2d.drawString(debugOutput, 20, 20 * i);
					}
				}
			}

			g2d.dispose();

			// Update the ScreenManagers BufferStrategy
			ScreenManager.update();

			FPSCounter.update(elapsedTime / 1000000000f);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stops the repaint-loop effectively terminating the thread.
	 */
	public void stop() {
		isRunning = false;
		resetDisplay();
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		start();
	}

	/**
	 * Sets the painter that should paint to the screen
	 * 
	 * @param painter
	 *            the painter to paint with
	 */
	public void setPainter(Painter painter) {
		this.painter = painter;
	}
	
	/**
	 * 
	 * @param enabled
	 */
	public void setDebugEnabled(boolean enabled) {
		debug = enabled;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		ScreenManager.setTitle(title);
	}

	/**
	 * 
	 * @param state
	 */
	public void setState(int state) {
		ScreenManager.setState(state);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setResolution(int width, int height) {
		ScreenManager.setRes(width, height);
	}
	
	/**
	 * 
	 */
	public void requestFocus(){
		ScreenManager.requestFocus();
	}

	/**
	 * @param inputHandler
	 */
	public void addInputListener(Input inputHandler) {
		ScreenManager.addInputListener(inputHandler);
	}
	
	/**
	 * 
	 * @param provider
	 */
	public void AddDebugText(DebugOutputProvider provider){
		debugPrintOuts.add(provider);
	}
}
