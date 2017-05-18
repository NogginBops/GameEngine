package game.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

import game.Game;
import game.debug.DebugOutputProvider;
import game.gameObject.graphics.Painter;
import game.input.Input;
import game.util.FPSCounter;

/**
 * A screen object manages repainting of a window
 * 
 * @version 2.0
 * @author Julius Häger
 */
public class Screen implements Runnable{
	
	//JAVADOC: Screen
	
	//FIXME: Full screen performance is really bad, find a way to fix this.
	// With a full HD res the frame rate never goes above 50fps
	// This might improve with the task system, but some other solution could need to be implemented.
	
	//TODO: Add lighting when the task system is implemented
	
	//NOTE: Should a multi-threaded rendering system be implemented before the task system is?
	// It would be a lot easier and would increase performance a lot (I think)
	
	//TODO: Add support for image effects and filters
	
	//TODO: Support image effects for individual painters
	

	/**
	 * @author Julius Häger
	 *
	 */
	public enum Mode{
		/**
		 * 
		 */
		NORMAL,
		/**
		 * 
		 */
		FULL_SCREEN,
		/**
		 * 
		 */
		BORDERLESS
	}
	
	/**
	 * The number of frames dropped since the start of the program.
	 */
	public static int framesDropped = 0;
	
	private JFrame frame;
	
	private static BufferStrategy strategy;
	
	private Insets insets;
	
	private String title;
	
	private Mode mode;
	
	//TODO: Find a good way to handle different resolutions v.s window size
	//Currently there is not any clarity to what changes the window size v.s the resolution.
	
	private Dimension size;
	private Dimension originalSize;
	
	private boolean resizable = false;
	
	private boolean shouldRun = false;
	
	private CopyOnWriteArrayList<Painter> painters;
	
	private CopyOnWriteArrayList<Input> inputListeners;
	
	private ArrayList<DebugOutputProvider> debugPrintOuts;
	
	private boolean debug = false;
	
	private int targetFPS;
	
	private boolean limitFPS = true;
	
	private boolean shouldApplyMode = false;
	
	/**
	 * @param size 
	 * @param mode
	 * @param title
	 * @param targetFPS 
	 */
	public Screen(Dimension size, Mode mode, String title, int targetFPS) {
		
		this.size = size;
		originalSize = size;
		this.mode = mode;
		this.title = title;
		
		this.targetFPS = targetFPS;
		
		painters = new CopyOnWriteArrayList<>();
		
		inputListeners = new CopyOnWriteArrayList<>();
		
		debugPrintOuts = new ArrayList<>();
		
		ApplyMode();
	}
	
	//TODO: Does this break any callbacks?
	private void ApplyMode(){
		if(frame != null){
			frame.dispose();
		}
		
		size = originalSize;
		
		frame = new JFrame(title);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
		DisplayMode displayMode = device.getDisplayMode();
		
		switch (mode) {
		case NORMAL:
			frame.setUndecorated(false);
			frame.setExtendedState(JFrame.NORMAL);
			frame.getContentPane().setPreferredSize(size);
			break;
		case FULL_SCREEN:
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			
			this.size = new Dimension(displayMode.getWidth(), displayMode.getHeight());
			
			frame.getContentPane().setPreferredSize(size);
			
			device.setFullScreenWindow(frame);
			break;
		case BORDERLESS:
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			
			frame.getContentPane().setPreferredSize(size);
			
			frame.pack();
			break;
		default:
			break;
		}
		
		frame.setResizable(resizable);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.createBufferStrategy(2);
		
		insets = frame.getInsets();
		
		for (Input input : inputListeners) {
			frame.addKeyListener(input);
			frame.addMouseListener(input);
			frame.addMouseMotionListener(input);
			frame.addMouseWheelListener(input);
		}
		
		shouldApplyMode = false;
	}
	
	@Override
	public void run() {
		shouldRun = true;
		
		long currentTime = System.nanoTime();
		long elapsedTime = 0;
		
		long sleepTime = 0;
		long targetTime = 0;
		
		while (shouldRun) {
			long time = System.nanoTime();
			elapsedTime = time - currentTime;
			currentTime = time;
			
			if (shouldApplyMode) {
				ApplyMode();
			}
			
			Graphics2D g2d = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();
			g2d.translate(insets.right, insets.top);
			
			//Get all painter images
			for (Painter painter : painters) {
				ScreenRect rect = painter.getScreenRectangle();
				
				g2d.drawImage(painter.getImage(), 
						(int) (rect.getX() * size.width), 
						(int) (rect.getY() * size.height), 
						(int) (rect.getX2() * size.width), 
						(int) (rect.getY2() * size.height),
						null);
			}
			
			//g2d.drawImage(image, 0, 0, null);
			
			if (debug) {
				g2d.setColor(Color.GREEN.brighter());
				int lines = 0;
				for (int i = 0; i < debugPrintOuts.size(); i++) {
					for (String debugOutput : debugPrintOuts.get(i).getDebugValues()) {
						lines++;
						g2d.drawString(debugOutput, 20, 20 * lines);
					}
				}
			}
			
			g2d.dispose();
			
			if (frame != null) {
				strategy = frame.getBufferStrategy();
				if (!strategy.contentsLost()) {
					strategy.show();
				} else {
					framesDropped++;
				}
			}
			
			FPSCounter.update(elapsedTime / 1000000000f);
			
			if((elapsedTime / 1000000f) > Game.deltaTimeWarningThreshold){
				Game.log.logWarning("Graphics loop delta time exeeded " +
						Game.deltaTimeWarningThreshold + "ms with " +
						((elapsedTime / 1000000f) - Game.deltaTimeWarningThreshold) + "ms",
						"Graphics", "DeltaTime", "System");
			}
			
			if(limitFPS){
				targetTime = (1000000000L / targetFPS);
				sleepTime = targetTime - (elapsedTime - sleepTime);
				
				if(sleepTime > 0){
					try {
						Thread.sleep(sleepTime / 1000000, (int)(sleepTime % 1000000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		frame.dispose();
	}
	
	/**
	 * 
	 */
	public void stop(){
		shouldRun = false;
	}
	
	/**
	 * 
	 * @param inputListener
	 */
	public void addInputListener(Input inputListener) {
		inputListeners.add(inputListener);
		
		frame.addKeyListener(inputListener);
		frame.addMouseListener(inputListener);
		frame.addMouseMotionListener(inputListener);
		frame.addMouseWheelListener(inputListener);
	}
	
	//NOTE: Is this method even useful?
	/**
	 * @return
	 */
	public Painter[] getPainters(){
		return painters.toArray(null);
	}
	
	/**
	 * @param painter
	 */
	public void addPainter(Painter painter){
		painters.add(painter);
	}
	
	/**
	 * @param painter
	 */
	public void removePainter(Painter painter){
		painters.remove(painter);
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
	 * @param provider
	 */
	public void addDebugText(DebugOutputProvider provider){
		debugPrintOuts.add(provider);
	}
	
	/**
	 * @param target
	 */
	public void setTargetFPS(int target){
		targetFPS = target;
	}
	
	/**
	 * @return
	 */
	public int getTargetFPS(){
		return targetFPS;
	}
	
	/**
	 * @param limit
	 */
	public void limitFPS(boolean limit){
		this.limitFPS = limit;
	}
	
	/**
	 * @return
	 */
	public boolean isLimitingFPS(){
		return limitFPS;
	}
	
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(title);
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return size.width;
	}

	/**
	 * @param width
	 */
	public void setWidth(int width) {
		size.width = width;
		originalSize = size;
		frame.getContentPane().setPreferredSize(size);
		frame.pack();
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return size.height;
	}

	/**
	 * @param height
	 */
	public void setHeight(int height) {
		size.height = height;
		originalSize = size;
		frame.getContentPane().setPreferredSize(size);
		frame.pack();
	}
	
	/**
	 * @return
	 */
	public Dimension getSize(){
		return size;
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height){
		size.setSize(width, height);
		frame.getContentPane().setPreferredSize(size);
		frame.pack();
	}
	
	
	/**
	 * @return
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		shouldApplyMode = true;
	}
	
	/**
	 * @return
	 */
	public Insets getInsets() {
		return insets;
	}
	
	/**
	 * 
	 */
	public void requestFocus(){
		frame.requestFocus();
	}
}
