package game.screen;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import game.input.Input;

/**
 * <p>
 * The {@link ScreenManager} is a utility class for working with a single
 * JFrame.
 * </p>
 * 
 * @version 1.0
 * @author Julius Häger
 */
public final class ScreenManager {

	// JAVADOC: ScreenManager

	/**
	 * 
	 */
	public static final int NORMAL = 0x00;

	/**
	 * 
	 */
	public static final int FULL_SCREEN = 0x01;

	private static JFrame frame;

	private static Input inputListener;

	private static Insets insets;

	private static int width;

	private static int height;

	private static int fullScreenWidth;

	private static int fullScreenHeight;

	private static int currentState;

	private static int state;

	private static BufferStrategy strategy;

	/**
	 * The number of frames dropped since the start of the program.
	 */
	public static int framesDropped = 0;

	/**
	 * Creates a frame with the specified mode.
	 * 
	 * @param width
	 *            the non full screen width of the window
	 * @param height
	 *            the non full screen height of the window
	 * @param state
	 *            the state of the window
	 * @param title
	 *            the title of the frame
	 */
	public static void createFrame(int width, int height, int state, String title) {
		frame = new JFrame(title);
		ScreenManager.width = width;
		ScreenManager.height = height;
		DisplayMode fullScreenDisplayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();
		fullScreenWidth = fullScreenDisplayMode.getWidth();
		fullScreenHeight = fullScreenDisplayMode.getHeight();
		setState(state);
	}

	/**
	 * <p>
	 * Sets the ScreenManagers current state to one of the available states.
	 * </p>
	 * <p>
	 * The available states are as follows.
	 * </p>
	 * <ul>
	 * <li>NORMAL - Uses the user set width and height</li>
	 * <li>FULL_SCREEN - Uses the displays default width and height</li>
	 * </ul>
	 * <p>
	 * <i>This disposes the current window and creates a new one.<br>
	 * This means that after the state is changed a new {@link Graphics2D} needs
	 * to be fetched.</i>
	 * </p>
	 * 
	 * @param state
	 *            the state to change to
	 */
	public static void setState(int state) {
		ScreenManager.state = state;
		UpdateFrame();
	}

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public static void setRes(int width, int height) {
		ScreenManager.width = width;
		ScreenManager.height = height;
		UpdateFrame();
	}

	private static void UpdateFrame() {
		String title = frame.getTitle();
		frame.dispose();
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (state == NORMAL) {
			frame.setUndecorated(false);
			frame.setExtendedState(JFrame.NORMAL);
			frame.setPreferredSize(new Dimension(width, height));
			currentState = NORMAL;
		} else if (state == FULL_SCREEN) {
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setPreferredSize(new Dimension(fullScreenWidth, fullScreenHeight));
			currentState = FULL_SCREEN;
		}
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.createBufferStrategy(2);
		addInputListener(inputListener);

		insets = frame.getInsets();
	}

	/**
	 * Disposes the ScreenManagers frame.
	 */
	public static void closeFrame() {
		frame.dispose();
	}

	/**
	 * 
	 * @return the width depending on the current mode
	 */
	public static int getWidth() {
		if (currentState == NORMAL) {
			return width;
		} else if (currentState == FULL_SCREEN) {
			return fullScreenWidth;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @return the height depending on the current mode
	 */
	public static int getHeight() {
		if (currentState == NORMAL) {
			return height;
		} else if (currentState == FULL_SCREEN) {
			return fullScreenHeight;
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * Gets a up to date {@link Graphics2D} object.
	 * </p>
	 * <p>
	 * This method should be called after changing the state of the
	 * ScreenManager to receive the new {@link Graphics2D} object.
	 * </p>
	 * <p>
	 * <i>The {@link Graphics2D} object should be disposed after use.</i>
	 * </p>
	 * 
	 * @return the active {@link Graphics2D} object
	 */
	public static Graphics2D getGraphics() {
		Graphics2D g2d = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();
		g2d.translate(insets.right, insets.top);
		return g2d;
	}

	/**
	 * <p>
	 * Updates the ScreenManager showing the {@link BufferStrategy}.
	 * </p>
	 */
	public static void update() {
		if (frame != null) {
			strategy = frame.getBufferStrategy();
			if (!strategy.contentsLost()) {
				strategy.show();
			} else {
				framesDropped++;
			}
		}
	}

	/**
	 * Returns the ScreenManager frames current insets.
	 * 
	 * @return The current insets.
	 */
	public static Insets getInsets() {
		return insets;
	}

	/**
	 * 
	 * @param inputListener
	 */
	public static void addInputListener(Input inputListener) {
		ScreenManager.inputListener = inputListener;
		frame.addKeyListener(inputListener);
		frame.addMouseListener(inputListener);
		frame.addMouseMotionListener(inputListener);
		frame.addMouseWheelListener(inputListener);
	}

	/**
	 * 
	 * @param title
	 */
	public static void setTitle(String title) {
		frame.setTitle(title);
	}
}
