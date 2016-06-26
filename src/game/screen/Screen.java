package game.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Game;
import game.debug.DebugOutputProvider;
import game.gameObject.graphics.Painter;
import game.input.Input;
import game.util.FPSCounter;
import game.util.image.ImageUtils;

/**
 * A screen object manages repainting of a window
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Screen implements Runnable {

	// JAVADOC: Screen
	
	//FIXME: Merge with ScreenManager!!!
	
	//FIXME: Full screen performance is really bad, find a way to fix this.
	//With a full HD res the frame rate never goes above 50fps
	//This might improve with the task system, but some other solution could need to be implemented.
	
	//TODO: Add lighting when the task system is implemented
	
	//NOTE: Should a multi-threaded rendering system be implemented before the task system is?
	//It would be a lot easier and would increase performance a lot (I think)

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
	 * 
	 * @param res
	 * @param state
	 * @param title
	 */
	public Screen(Dimension res, int state, String title) {
		ScreenManager.createFrame(res.width, res.height, state, title);
		
		image = new BufferedImage(res.width, res.height, BufferedImage.TYPE_INT_ARGB);
		image = ImageUtils.toSystemCompatibleImage(image);
		
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
	
	//TODO: Remove
	int i = 0;
	
	//NOTE: This method has no reason to be its own method
	private void loop() {
		long currentTime = System.nanoTime();
		long elapsedTime = 0;
		
		while (isRunning) {
			
			elapsedTime = System.nanoTime() - currentTime;
			currentTime = System.nanoTime();
			
			g2d = ScreenManager.getGraphics();
			
			//FIXME: Figure out what should and shouldn't be done.
			
			//image = new BufferedImage(ScreenManager.getWidth(), ScreenManager.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			//g2d.fillRect(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			if(image.getWidth() != ScreenManager.getWidth() || image.getHeight() != ScreenManager.getHeight()){
				image = new BufferedImage(ScreenManager.getWidth(), ScreenManager.getHeight(), BufferedImage.TYPE_INT_ARGB);
				image = ImageUtils.toSystemCompatibleImage(image);
				if(imageGraphics != null){
					imageGraphics.dispose();
					imageGraphics = null;
				}
			}
			
			if(imageGraphics == null){
				imageGraphics = image.createGraphics();
			}
			
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
			
			//imageGraphics.dispose();
			
			g2d.drawImage(image, 0, 0, null);
			
			//TODO: Better on screen debug
			
			if (debug) {
				g2d.setColor(Color.GREEN.brighter());
				int lines = 0;
				for (int i = 0; i < debugPrintOuts.size(); i++) {
					for (String debugOutput : debugPrintOuts.get(i).GetDebugValues()) {
						lines++;
						g2d.drawString(debugOutput, 20, 20 * lines);
					}
				}
			}

			g2d.dispose();

			// Update the ScreenManagers BufferStrategy
			ScreenManager.update();

			FPSCounter.update(elapsedTime / 1000000000f);
			
			if((elapsedTime / 1000000f) > Game.deltaTimeWarningThreshold){
				Game.log.logWarning("Graphics loop delta time exeeded " +
						Game.deltaTimeWarningThreshold + "ms with " +
						((elapsedTime / 1000000f) - Game.deltaTimeWarningThreshold) + "ms",
						"Graphics", "DeltaTime", "System");
			}
			
			//FIXME: THERE IS A BIG GC ISSUE WITH PARTICLE SYSTEMS, THIS IS A ADHOC SOLUTION!!
			i++;
			
			if(i % 100 == 0){
				i = 0;
				
				//System.gc();
			}
			
			//TODO: Dynamic sleep time
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//closeRequested == true, clean up
		
		imageGraphics.dispose();
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
			imageGraphics.dispose();
			imageGraphics = image.createGraphics();
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
