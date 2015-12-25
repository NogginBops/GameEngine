package game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import demos.pong.Ball;
import demos.pong.Pad;
import demos.pong.Pad.Side;
import demos.verticalScroller.Ship;
import demos.verticalScroller.ShipFactory;
import demos.pong.Score;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.UI.UI;
import game.UI.border.Border;
import game.UI.border.SolidBorder;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.image.UIImage;
import game.UI.elements.text.UILabel;
import game.debug.IDHandlerDebugFrame;
import game.gameObject.GameObject;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Paintable;
import game.gameObject.graphics.UniformSpriteSheet;
import game.gameObject.physics.PhysicsEngine;
import game.input.Input;
import game.input.KeyInputHandler;
import game.input.MouseInputHandler;
import game.screen.Screen;
import game.screen.ScreenManager;
import game.sound.AudioEngine;
import game.test.GameObjectAdder;
import game.test.GameObjectAdderWithAudio;
import game.test.OtherPaintable;
import game.test.TestAnimationSprite;
import game.test.TestInputSprite;
import game.test.TestSprite;
import game.util.GameObjectHandler;
import game.util.IDHandler;
import game.util.UpdateCounter;
import game.util.Updater;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Game extends Updater {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	// TODO: Clean up

	// JAVADOC: Game

	/**
	 * 
	 */
	public static Game game;

	private static boolean running = false;
	private static boolean closeRequested = false;
	private static boolean paused = false;

	private GameObjectHandler gameObjectHandler;

	private PhysicsEngine physicsEngine;

	private Camera camera;

	private Screen screen;

	private long initTime;

	private IDHandlerDebugFrame<GameObject> IDDebug;

	/**
	 * 
	 */
	public Game() {
		initTime = System.nanoTime();

		basicSetup();

		// test();
		// test2();
		// test2WithAudio();
		// pong();
		// pong44();
		// breakout();
		// UITest();
		
		verticalScroller();

		//completeSetup();
	}
	
	private void verticalScroller(){
		screen.setResolution(400, 600);
		
		camera.setSize(400, 600);
		
		screen.setDebugEnabled(true);
		camera.receiveKeyboardInput(false);
		
		camera.setBackgroundColor(new Color(60, 91, 120));
		
		BufferedImage shipSheetImage = null;
		
		BufferedImage projectileSheetImage = null;
		
		try {
			shipSheetImage = IOHandler.load(new LoadRequest<BufferedImage>("ShipSheet", new File("./res/verticalScroller/ShipsSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
			projectileSheetImage = IOHandler.load(new LoadRequest<BufferedImage>("ProjectileSheet", new File("./res/verticalScroller/ProjectileSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UniformSpriteSheet shipSheet = new UniformSpriteSheet(shipSheetImage, 12, 14, new Color(191, 220, 191));
		
		UniformSpriteSheet projectileSheet = new UniformSpriteSheet(projectileSheetImage, 12, 14, new Color(191, 220, 191));
		
		System.out.println("Horizontal tiles: " + shipSheet.getHorizontalTiles() + " Vertical tiles: " + shipSheet.getVerticalTiles());
		
		ShipFactory.createShip("Standard", 
				shipSheet.getSprite(0, 6, 2, 8), shipSheet.getSprite(2, 6, 4, 8), shipSheet.getSprite(4, 6, 6, 8), shipSheet.getSprite(6, 6, 8, 8), shipSheet.getSprite(8, 6, 10, 8), projectileSheet.getSprite(3, 4));
		
		Ship ship = ShipFactory.getShip("Standard");
		
		ship.setLocation(130, 100);
		
		gameObjectHandler.addGameObject(ship, "PlayerShip");
		addUpdateListener(ship);
	}

	private void basicSetup() {
		gameObjectHandler = new GameObjectHandler();
		
		physicsEngine = new PhysicsEngine(gameObjectHandler);
		
		addUpdateListener(physicsEngine);

		screen = new Screen(600, 400, ScreenManager.NORMAL, "Game");

		camera = new Camera(gameObjectHandler, 0, 0, ScreenManager.getWidth() - ScreenManager.getInsets().right,
				ScreenManager.getHeight() - ScreenManager.getInsets().top);

		gameObjectHandler.addGameObject(camera, "Main camera");
		addUpdateListener(camera);

		screen.setPainter(camera);

		MouseInputHandler mouseHandeler = new MouseInputHandler(gameObjectHandler, camera);

		KeyInputHandler keyHandeler = new KeyInputHandler(gameObjectHandler);

		Input inputHandeler = new Input(mouseHandeler, keyHandeler);

		addUpdateListener(inputHandeler);

		ScreenManager.addInputListener(inputHandeler);

		PhysicsEngine physicsEngine = new PhysicsEngine(gameObjectHandler);

		addUpdateListener(physicsEngine);

		AudioEngine.init(camera);

		Game.game = this;
	}

	private void completeSetup() {
		addIDHandlerDebug();
	}

	// TODO: Fix proper onStart onExit and other similar methods

	private void onQuit() {
		IDDebug.stoppDebug();
	}
	
	private void UITest() {
		screen.setTitle("UI Test");
		screen.setDebugEnabled(true);

		camera.receiveKeyboardInput(true);

		BasicUIContainer container = new BasicUIContainer(200, 300);

		Border border = new SolidBorder(20, Color.MAGENTA);

		container.setBorder(border);

		BasicUIContainer container2 = new BasicUIContainer(100, 100);

		Border border2 = new SolidBorder(10, Color.CYAN);

		container2.setBorder(border2);

		container.addUIElement(container2);

		UILabel lable = new UILabel("Test label");

		lable.setColor(Color.WHITE);

		container2.addUIElement(lable);
		
		Image image;
		try {
			image = IOHandler.load(new LoadRequest<BufferedImage>("Image", new File("./res/Background.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			image = null;
		}
		
		UIImage UIimg = new UIImage(0, 0, 40, 100, image);
		
		UIimg.setNativeSize();
		
		UIimg.setZOrder(2);
		
		container.addUIElement(UIimg);

		UI hud = new UI(new Rectangle(400, 200, 400, 400), container);

		gameObjectHandler.addGameObject(hud);
	}

	@SuppressWarnings("unused")
	private void pong() {
		screen.setTitle("Pong");

		Pad rightPad = new Pad(ScreenManager.getWidth() - 60, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad, "RightPad");
		addUpdateListener(rightPad);

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad, "LeftPad");
		addUpdateListener(leftPad);

		Ball ball = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball, "Ball");
		addUpdateListener(ball);

		ball.resetBall();

		Score score = new Score(camera.getBounds());

		gameObjectHandler.addGameObject(score, "Score");
		addUpdateListener(score);
	}

	@SuppressWarnings("unused")
	private void pong44() {
		screen.setTitle("Pong44");

		Pad rightPad = new Pad(ScreenManager.getWidth() - 50, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad, "RightPad");
		addUpdateListener(rightPad);

		Pad rightPad2 = new Pad(ScreenManager.getWidth() - 100, 40, 10, 50, KeyEvent.VK_O, KeyEvent.VK_L,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad2, "RightPad2");
		addUpdateListener(rightPad2);

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad, "LeftPad");
		addUpdateListener(leftPad);

		Pad leftPad2 = new Pad(100, 40, 10, 50, KeyEvent.VK_T, KeyEvent.VK_G, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad2, "LeftPad2");
		addUpdateListener(leftPad2);

		Ball ball = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball, "Ball");
		addUpdateListener(ball);

		Ball ball2 = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball2, "Ball2");
		addUpdateListener(ball2);

		ball.resetBall();

		ball2.resetBall();

		Score score = new Score(camera.getBounds());

		gameObjectHandler.addGameObject(score, "Score");
		addUpdateListener(score);
	}

	@SuppressWarnings("unused")
	private void test() {
		screen.setTitle("Test #1");

		screen.setDebugEnabled(true);

		camera.receiveKeyboardInput(true);

		OtherPaintable z1 = new OtherPaintable(0, 0, 100, 100, 1, Color.BLUE);

		OtherPaintable z2 = new OtherPaintable(10, 10, 100, 100, 2, Color.RED);

		OtherPaintable z3 = new OtherPaintable(20, 20, 100, 100, 3, Color.GREEN);

		gameObjectHandler.addGameObject(z2, "OtherPaintable2");

		gameObjectHandler.addGameObject(z3, "OtherPaintable3");

		gameObjectHandler.addGameObject(z1, "OtherPaintable1");

		TestSprite t = new TestSprite(50, 50, 100, 70);

		gameObjectHandler.addGameObject(t, "TestSprite1");
		addUpdateListener(t);

		t.setDX(30);
		t.setDY(3);

		TestSprite t2 = new TestSprite(400, 50, 20, 20);

		gameObjectHandler.addGameObject(t2, "TestSprite2");
		addUpdateListener(t2);

		t2.setDX(-60);
		t2.setDY(-1);

		TestInputSprite testInput = new TestInputSprite(100, 100, 100, 100, 10, false);

		gameObjectHandler.addGameObject(testInput, "TestInputSprite1");
		addUpdateListener(testInput);

		TestInputSprite testInput2 = new TestInputSprite(200, 150, 100, 100, 9, false);

		gameObjectHandler.addGameObject(testInput2, "TestInuptSprite2");
		addUpdateListener(testInput2);

		Random rand = new Random();

		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 30; y++) {
				TestSprite test = new TestSprite(x * 30, y * 30, 20, 20);
				gameObjectHandler.addGameObject(test);
				addUpdateListener(test);
				test.setDX(rand.nextFloat() * 50);
				test.setDY(rand.nextFloat() * 50);
			}
		}
	}

	@SuppressWarnings("unused")
	private void test2() {
		screen.setTitle("Test #2");

		screen.setDebugEnabled(true);

		camera.receiveKeyboardInput(true);

		GameObjectAdder adder = new GameObjectAdder(gameObjectHandler);
		gameObjectHandler.addGameObject(adder);

	}

	@SuppressWarnings("unused")
	private void test2WithAudio() {
		screen.setTitle("Test #2");

		screen.setDebugEnabled(true);

		camera.receiveKeyboardInput(true);

		GameObjectAdderWithAudio adder = new GameObjectAdderWithAudio(-5, -5, gameObjectHandler);
		gameObjectHandler.addGameObject(adder);

		AudioEngine.setAudioListener(adder);
	}

	private void addIDHandlerDebug() {
		new Thread(IDDebug = new IDHandlerDebugFrame<>(gameObjectHandler.getIDHandler())).start();
	}

	/**
	 * Returns a list of all the currently registered {@link Paintable
	 * Paintables}. (Used by {@link game.gameObject.graphics.Camera Camera})
	 * 
	 * @return a list of the currently registered {@link Paintable Paintables}.
	 */
	public CopyOnWriteArrayList<Paintable> getPaintables() {
		return gameObjectHandler.getAllGameObjectsExtending(Paintable.class);
	}

	/**
	 * Starts the main loop of the game
	 */
	public void run() {
		System.out.println("Starting...");

		new Thread(screen).start();
		long startTime = System.nanoTime();
		long currTime = startTime;
		long elapsedTime;

		System.out.println("Pre run time: " + (startTime - initTime) / 1000000000f);

		System.out.println("Running!");
		running = true;
		while (running) {
			elapsedTime = System.nanoTime() - currTime;
			currTime += elapsedTime;

			if (closeRequested) {
				screen.stop();
				running = false;
			}

			if (paused)
				continue;

			boolean objectsChangedBefore = gameObjectHandler.haveObjectsChanged();

			propogateUpdate(elapsedTime);

			boolean objectsChangedAfter = gameObjectHandler.haveObjectsChanged();

			UpdateCounter.update(elapsedTime / 1000000000f);

			if (objectsChangedBefore && !objectsChangedAfter) {
				gameObjectHandler.clearChange();
			}
		}
		onQuit();
		System.out.println("Stopped.");
	}

	/**
	 * Requests a close or a stop in the game loop effectively ending the game
	 */
	public static void stop() {
		closeRequested = true;
		System.out.println("Close requested.");
	}

	/**
	 * Pauses the game so that it can be resumed later
	 */
	public static void pause() {
		// TODO: Game: Pause
		paused = true;
		System.out.println("Game paused");
	}

	/**
	 * Resumes the game if it was paused. If the game wasn't paused this method
	 * dosen't do anything
	 */
	public static void resume() {
		if (paused) {
			// TODO: Game: Resume
			paused = false;
			System.out.println("Game resumed");
		}
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isPaused() {
		return paused;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isRunning() {
		return running;
	}

	/**
	 * 
	 * @return
	 */
	private GameObjectHandler getObjectHandler() {
		return gameObjectHandler;
	}

	/**
	 * 
	 * @return
	 */
	public static GameObjectHandler getGameObjectHandler() {
		return game.getObjectHandler();
	}

	/**
	 * 
	 * @return
	 */
	public static IDHandler<GameObject> getCurrentIDHandler() {
		return game.getObjectHandler().getIDHandler();
	}
}
