package game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.stream.XMLStreamException;

import demos.pong.Ball;
import demos.pong.Pad;
import demos.pong.Pad.Side;
import demos.pong.Score;
import demos.verticalScroller.Ship;
import demos.verticalScroller.ShipFactory;
import demos.verticalScroller.map.Map;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.UI.UI;
import game.UI.border.Border;
import game.UI.border.SolidBorder;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.image.UIImage;
import game.UI.elements.input.UIButton;
import game.UI.elements.text.UILabel;
import game.controller.event.EventMachine;
import game.controller.event.engineEvents.GameQuitEvent;
import game.controller.event.engineEvents.GameStartEvent;
import game.debug.IDHandlerDebugFrame;
import game.debug.log.Log;
import game.debug.log.frame.LogFrame;
import game.gameObject.GameObject;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Paintable;
import game.gameObject.graphics.UniformSpriteSheet;
import game.gameObject.handler.GameObjectHandler;
import game.gameObject.physics.PhysicsEngine;
import game.input.Input;
import game.input.KeyInputHandler;
import game.input.MouseInputHandler;
import game.screen.Screen;
import game.screen.ScreenManager;
import game.screen.ScreenRect;
import game.sound.AudioEngine;
import game.test.GameObjectAdder;
import game.test.GameObjectAdderWithAudio;
import game.test.OtherPaintable;
import game.test.TestInputSprite;
import game.test.TestSprite;
import game.util.FPSCounter;
import game.util.IDHandler;
import game.util.UpdateCounter;
import game.util.UpdateListener;
import game.util.Updater;
import kuusisto.tinysound.Music;

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
	
	/**
	 * The debug log associated with the game
	 */
	public static Log log;
	
	/**
	 * The main EventMachine
	 */
	public static EventMachine eventMachine;

	private static boolean running = false;
	private static boolean closeRequested = false;
	private static boolean paused = false;
	
	private String name = "Game";

	private GameObjectHandler gameObjectHandler;

	private PhysicsEngine physicsEngine;
	
	private Camera camera;

	private Screen screen;

	private long initTime;

	private LogFrame LogFrame;
	
	private IDHandlerDebugFrame<GameObject> IDDebug;
	
	//FIXME: Giant GC freeze

	/**
	 * 
	 */
	public Game() {
		initTime = System.nanoTime();

		basicSetup();
		
		basicDebug();

		test();
		//test2();
		// test2WithAudio();
		// pong();
		// pong44();
		// breakout();
		//UITest();
		
		//verticalScroller();
		
		cameraTest();
		//cameraTest2();
		
		completeSetup();
		
		//addDebug();
	}
	
	private void cameraTest(){
		setName("Camera Test");
		
		Camera newCamera = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		newCamera.setScreenRectangle(new ScreenRect(0.59f, 0.59f, 0.4f, 0.4f));
		
		newCamera.setBackgroundColor(new Color(20, 200, 100, 100));
		
		screen.addPainter(newCamera);
		
		gameObjectHandler.addGameObject(newCamera, "Secondary camera");
	}
	
	@SuppressWarnings("unused")
	private void cameraTest2(){
		setName("Camera Test 2");
		
		Camera Q1 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q1.setScreenRectangle(new ScreenRect(0, 0, 0.5f, 0.5f));
		
		Q1.setBackgroundColor(Color.CYAN);
		
		Q1.receiveKeyboardInput(true);
		
		screen.addPainter(Q1);
		
		gameObjectHandler.addGameObject(Q1, "Q1 camera");
		
		Camera Q2 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q2.setScreenRectangle(new ScreenRect(0.5f, 0, 0.5f, 0.5f));
		
		Q2.setBackgroundColor(Color.MAGENTA);
		
		Q2.receiveKeyboardInput(true);
		
		screen.addPainter(Q2);
		
		gameObjectHandler.addGameObject(Q2, "Q2 camera");
		
		Camera Q3 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q3.setScreenRectangle(new ScreenRect(0, 0.5f, 0.5f, 0.5f));
		
		Q3.setBackgroundColor(Color.YELLOW);
		
		Q3.receiveKeyboardInput(true);
		
		screen.addPainter(Q3);
		
		gameObjectHandler.addGameObject(Q3, "Q3 camera");
		
		Camera Q4 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q4.setScreenRectangle(new ScreenRect(0.5f, 0.5f, 0.5f, 0.5f));
		
		Q4.setBackgroundColor(Color.BLACK);
		
		Q4.receiveKeyboardInput(true);
		
		screen.addPainter(Q4);
		
		gameObjectHandler.addGameObject(Q4, "Q4 camera");
	}
	
	@SuppressWarnings("unused")
	private void verticalScroller(){
		setName("VerticalScroller");
		
		screen.setResolution(400, 600);
		
		camera.setSize(400, 600);
		
		screen.setDebugEnabled(true);
		camera.receiveKeyboardInput(false);
		
		camera.setBackgroundColor(new Color(80, 111, 140));
		
		BufferedImage shipSheetImage = null;
		
		BufferedImage projectileSheetImage = null;
		
		try {
			shipSheetImage = IOHandler.load(new LoadRequest<BufferedImage>("ShipSheet", new File("./res/verticalScroller/graphics/ShipsSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
			projectileSheetImage = IOHandler.load(new LoadRequest<BufferedImage>("ProjectileSheet", new File("./res/verticalScroller/graphics/ProjectileSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UniformSpriteSheet shipSheet = new UniformSpriteSheet(shipSheetImage, 12, 14, new Color(191, 220, 191));
		
		UniformSpriteSheet projectileSheet = new UniformSpriteSheet(projectileSheetImage, 12, 14, new Color(191, 220, 191));
		
		log.logMessage("Horizontal tiles: " + shipSheet.getHorizontalTiles() + " Vertical tiles: " + shipSheet.getVerticalTiles(), "VerticalScroller");
		
		ShipFactory.createShip("Standard", 
				shipSheet.getSprite(0, 6, 2, 8),
				shipSheet.getSprite(2, 6, 4, 8),
				shipSheet.getSprite(4, 6, 6, 8),
				shipSheet.getSprite(6, 6, 8, 8),
				shipSheet.getSprite(8, 6, 10, 8),
				projectileSheet.getSprite(3, 4));
		
		Ship ship = ShipFactory.getShip("Standard");
		
		ship.setMovmentBounds(camera.getBounds());
		
		ship.setLocation((camera.getWidth() - ship.getBounds().width)/2, camera.getHeight() - 150);
		
		gameObjectHandler.addGameObject(ship, "PlayerShip");
		
		AudioEngine.setAudioListener(ship);
		
		try {
			Map map = Map.parseMap(new File(".\\res\\verticalScroller\\maps\\map1.xml"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (XMLStreamException e1) {
			e1.printStackTrace();
		}
		
		//TODO: Fix adhoc solution
		try {
			Music music = IOHandler.load(new LoadRequest<Music>("MainMusic", new File(".\\res\\verticalScroller\\sounds\\music\\fight_looped.wav"), Music.class, "DefaultMusicLoader")).result;
			music.play(true, 0.4f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AudioEngine.setMasterVolume(0.01f);
	}

	private void basicSetup() {
		Game.game = this;
		
		eventMachine = new EventMachine();
		
		log = new Log();
		
		gameObjectHandler = new GameObjectHandler();
		
		physicsEngine = new PhysicsEngine(gameObjectHandler);
		
		screen = new Screen(600, 400, ScreenManager.NORMAL, "Game");
		camera = new Camera(0, 0, ScreenManager.getWidth(), ScreenManager.getHeight());
		
		camera.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
		
		MouseInputHandler mouseHandler = new MouseInputHandler(gameObjectHandler, camera);
		KeyInputHandler keyHandler = new KeyInputHandler(gameObjectHandler);
		Input inputHandler = new Input(mouseHandler, keyHandler);
		
		screen.addPainter(camera);
		screen.addInputListener(inputHandler);

		AudioEngine.init(camera);
		
		gameObjectHandler.addGameObject(physicsEngine, "Physics Engine");
		
		gameObjectHandler.addGameObject(inputHandler, "Input Handler");
		
		gameObjectHandler.addGameObject(camera, "Main camera");
	}
	
	//FIXME: This is a memory intensive solution and has to go
	private void basicDebug(){
		screen.addDebugText(() -> { return new String[]{
				"Frames: " + FPSCounter.framesTot,
				"Updates: " + UpdateCounter.updatesTot,
				"ElapsedTime: " + 0,
				"Time: " + FPSCounter.timeTot,
				"FPS: " + FPSCounter.fps,
				"Average FPS: " + FPSCounter.averageFPS,
				"Frames dropped: " + ScreenManager.framesDropped,
				"UPS: " + UpdateCounter.ups,
				"Average UPS: " + UpdateCounter.averageUPS,
				"Camera X: " + camera.getBounds().x,
				"Camera Y: " + camera.getBounds().y,
				"Objects: " + gameObjectHandler.numberOfGameObjects()
		};});
	}

	private void completeSetup() {
		//TODO: Is this needed/should it be a event?
	}
	
	@SuppressWarnings("unused")
	private void addDebug(){
		addDebugLog();
		addIDHandlerDebug();
	}
	
	// TODO: Fix proper onStart onExit and other similar methods. (USE EVENTS!!)
	
	private void onQuit() {
		if(IDDebug != null){
			IDDebug.stopDebug();
		}
		
		if(LogFrame != null){
			LogFrame.stopDebug();
		}
		
		eventMachine.fireEvent(new GameQuitEvent(this, name));
	}
	
	@SuppressWarnings("unused")
	private void UITest() {
		screen.setTitle("UI Test");
		screen.setDebugEnabled(true);

		camera.receiveKeyboardInput(true);
		
		UI hud = new UI(new Rectangle(200, 100, 400, 400));

		BasicUIContainer container = new BasicUIContainer(200, 300);
		Border border = new SolidBorder(20, Color.MAGENTA);
		container.setBorder(border);
		hud.addUIElement(container);

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
		
		UIButton button = new UIButton(40, 40, 100, 40);
		gameObjectHandler.addGameObject(button);
		button.setZOrder(10);
		container.addUIElement(button);
		
		gameObjectHandler.addGameObject(hud);
	}

	@SuppressWarnings("unused")
	private void pong() {
		screen.setDebugEnabled(true);
		
		screen.setTitle("Pong");

		Pad rightPad = new Pad(camera.getWidth() - 60, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad, "RightPad");

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad, "LeftPad");

		Ball ball = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball, "Ball");

		ball.resetBall();

		Score score = new Score(camera.getBounds());

		gameObjectHandler.addGameObject(score, "Score");
	}

	@SuppressWarnings("unused")
	private void pong44() {
		screen.setDebugEnabled(true);
		
		screen.setTitle("Pong44");

		Pad rightPad = new Pad(ScreenManager.getWidth() - 50, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad, "RightPad");

		Pad rightPad2 = new Pad(ScreenManager.getWidth() - 100, 40, 10, 50, KeyEvent.VK_O, KeyEvent.VK_L,
				camera.getBounds(), Side.RIGHT);

		gameObjectHandler.addGameObject(rightPad2, "RightPad2");

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad, "LeftPad");

		Pad leftPad2 = new Pad(100, 40, 10, 50, KeyEvent.VK_T, KeyEvent.VK_G, camera.getBounds(), Side.LEFT);

		gameObjectHandler.addGameObject(leftPad2, "LeftPad2");

		Ball ball = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball, "Ball");

		Ball ball2 = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		gameObjectHandler.addGameObject(ball2, "Ball2");

		ball.resetBall();

		ball2.resetBall();

		Score score = new Score(camera.getBounds());

		gameObjectHandler.addGameObject(score, "Score");
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

		t.setDX(30);
		t.setDY(3);

		TestSprite t2 = new TestSprite(400, 50, 20, 20);

		gameObjectHandler.addGameObject(t2, "TestSprite2");

		t2.setDX(-60);
		t2.setDY(-1);

		TestInputSprite testInput = new TestInputSprite(100, 100, 100, 100, 10, false);

		gameObjectHandler.addGameObject(testInput, "TestInputSprite1");

		TestInputSprite testInput2 = new TestInputSprite(200, 150, 100, 100, 9, false);

		gameObjectHandler.addGameObject(testInput2, "TestInuptSprite2");

		Random rand = new Random();

		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 30; y++) {
				TestSprite test = new TestSprite(x * 30, y * 30, 20, 20);
				gameObjectHandler.addGameObject(test);
				
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

	private void addDebugLog(){
		new Thread(LogFrame = new LogFrame(log), "Debug log").start();
	}
	
	private void addIDHandlerDebug() {
		new Thread(IDDebug = new IDHandlerDebugFrame<>(gameObjectHandler.getIDHandler()), "ID Handler Debug").start();
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
		Thread.currentThread().setName(name);
		
		log.logMessage("Starting...", "System");
		//Maybe onStart method?
		eventMachine.fireEvent(new GameStartEvent(this, name));

		new Thread(screen, "Graphics").start();
		long startTime = System.nanoTime();
		long currTime = startTime;
		long elapsedTime;
		
		log.logMessage("Pre run time: " + (startTime - initTime) / 1000000000f, "System");
		
		//FIXME: Allocating a lot of memory when updating many GameObjects

		log.logMessage("Running!", "System");
		running = true;
		while (running) {
			elapsedTime = System.nanoTime() - currTime;
			currTime += elapsedTime;

			if (closeRequested) {
				screen.stop();
				running = false;
			}

			if (paused){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			if(gameObjectHandler.shouldUpdateObjects()){
				listeners = gameObjectHandler.getAllGameObjectsExtending(UpdateListener.class);
			}
			
			propagateUpdate(elapsedTime);
			
			UpdateCounter.update(elapsedTime / 1000000000f);
			
			gameObjectHandler.clearChange();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		onQuit();
		log.logMessage("Stopped.", "System");
	}

	/**
	 * Requests a close or a stop in the game loop effectively ending the game
	 */
	public static void stop() {
		closeRequested = true;
		log.logMessage("Close requested.", "System");
	}

	/**
	 * Pauses the game so that it can be resumed later
	 */
	public static void pause() {
		if (!paused) {
			paused = true;
			log.logMessage("Game paused", "System");
		}
	}

	/**
	 * Resumes the game if it was paused. If the game wasn't paused this method
	 * dosen't do anything
	 */
	public static void resume() {
		if (paused) {
			paused = false;
			log.logMessage("Game resumed", "System");
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
	
	/**
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getName(){
		return name;
	}
}
