package game.screen;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import game.Game;
import game.debug.DebugOutputProvider;
import game.debug.log.Log;
import game.gameObject.graphics.Painter;
import game.input.Input;
import game.util.FPSCounter;

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
	
	//TODO: Add support for image effects and filters
	
	//TODO: Support image effects for individual painters
	
	private ArrayList<Painter> painters;
	
	private ArrayList<DebugOutputProvider> debugPrintOuts;
	
	private BufferedImage image;
	
	//Temporary variable
	private ScreenRect rect;

	/**
	 * @param width
	 * @param height
	 * @param state
	 * @param title
	 */
	public Screen(int width, int height, int state, String title) {
		ScreenManager.createFrame(width, height, state, title);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		painters = new ArrayList<Painter>();
		
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
	
	Graphics2D imageGraphics;
	
	private void loop() {
		long currentTime = System.nanoTime();
		long elapsedTime = 0;
		
		while (isRunning) {
			
			elapsedTime = System.nanoTime() - currentTime;
			currentTime = System.nanoTime();
			
			g2d = ScreenManager.getGraphics();
			
			//FIXME: Figure out what should and shouldn't be done.
			
			//g2d.clearRect(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
			
			//image = new BufferedImage(ScreenManager.getWidth(), ScreenManager.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			//g2d.fillRect(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			
			imageGraphics = image.createGraphics();
			
			//imageGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			//imageGraphics.fillRect(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
			//imageGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			
			//Get all painter images
			for (Painter painter : painters) {
				rect = painter.getScreenRectangle();
				
				imageGraphics.drawImage(painter.getImage(), 
						(int) (rect.getX() * ScreenManager.getWidth()), 
						(int) (rect.getY() * ScreenManager.getHeight()), 
						(int) (rect.getX2() * ScreenManager.getWidth()), 
						(int) (rect.getY2() * ScreenManager.getHeight()),
						null);
			}
			
			imageGraphics.dispose();
			
			g2d.drawImage(image, 0, 0, ScreenManager.getWidth(), ScreenManager.getHeight(), null);
			
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
	public void addPainter(Painter painter) {
		this.painters.add(painter);
	}
	
	/**
	 * 
	 * @param painter
	 */
	public void removePainter(Painter painter){
		this.painters.remove(painter);
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
		
		synchronized (image) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
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
	public void addDebugText(DebugOutputProvider provider){
		debugPrintOuts.add(provider);
	}
}
