package game.screen;

import game.Game;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Painter;
import game.util.FPSCounter;
import game.util.UpdateCounter;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A screen object manages repainting of a window
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Screen implements Runnable {

	// JAVADOC: Screen
	
	//TODO: Merge with ScreenManager

	// TODO: Multiple cameras (Painters) with viewport rectangles
	
	// TODO: Add better debugging system

	private boolean isRunning = false;

	private boolean debug = false;

	private Graphics2D g2d;

	private Painter painter;

	/**
	 * @param width
	 * @param height
	 * @param state
	 * @param title
	 */
	public Screen(int width, int height, int state, String title) {
		ScreenManager.createFrame(width, height, state, title);
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
				g2d.drawString(("Frame: " + FPSCounter.framesTot), 20, 20);
				g2d.drawString(("Updates: " + UpdateCounter.updatesTot), 20, 40);
				g2d.drawString(("ElapsedTime: " + elapsedTime), 20, 60);
				g2d.drawString(("Time: " + FPSCounter.timeTot), 20, 80);
				g2d.drawString(("FPS: " + FPSCounter.fps), 20, 100);
				g2d.drawString(("Average FPS: " + FPSCounter.averageFPS), 20, 120);
				g2d.drawString("Frames dropped: " + ScreenManager.framesDropped, 20, 140);
				g2d.drawString(("UPS: " + UpdateCounter.ups), 20, 160);
				g2d.drawString(("Average UPS: " + UpdateCounter.averageUPS), 20, 180);
				g2d.drawString("Camera X: " + ((Camera) painter).getBounds().x, 20, 200);
				g2d.drawString("Camera Y: " + ((Camera) painter).getBounds().y, 20, 220);
				g2d.drawString("Objects: " + Game.getGameObjectHandler().numberOfGameObjects(), 20, 240);
			}

			g2d.dispose();

			// Update the ScreenManagers BufferStrategy
			ScreenManager.update();

			FPSCounter.update(elapsedTime / 1000000000f);
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
	
	public void requestFocus(){
		ScreenManager.requestFocus();
	}
}
