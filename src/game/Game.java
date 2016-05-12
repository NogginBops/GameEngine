package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
import game.debug.log.LogMessage.LogImportance;
import game.debug.log.frame.LogDebugFrame;
import game.gameObject.GameObject;
import game.gameObject.graphics.Camera;
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

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Game extends Updater {

	//TODO: Restructure project packages to make more sense
	
	//TODO: Externalize start
	
	//TODO: Move all demos out of this file
	
	//NOTE: Should everything be static? There is only ever going to be one game.
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	// TODO: Clean up

	// JAVADOC: Game

	private static boolean running = false;
	private static boolean closeRequested = false;
	private static boolean paused = false;
	
	private long startTime;
	private long currTime;
	private long elapsedTime;
	
	private String name = "Game";

	/**
	 * The current Game.
	 */
	public static Game game;
	
	/**
	 * The debug log associated with the game.
	 */
	public static Log log = new Log();
	
	/**
	 * The main EventMachine.
	 */
	public static EventMachine eventMachine = new EventMachine();
	
	//TODO: Some more elegant methods for using the GOH
	/**
	 * The main gameObjectHandeler.
	 */
	public static GameObjectHandler gameObjectHandler = new GameObjectHandler();

	private PhysicsEngine physicsEngine; //Make static?
	
	private Camera camera; //TODO: This should support multiple cameras!

	public Screen screen; //FIXME: Support multiple screens.
	
	private MouseInputHandler mouseHandler;
	
	private KeyInputHandler keyHandler;
	
	private Input inputHandler;

	private long initTime;

	private LogDebugFrame LogDebugFrame;
	
	private IDHandlerDebugFrame<GameObject> IDDebug;
	
	//FIXME: Giant GC freeze

	/**
	 * @param settings
	 */
	public Game(GameSettings settings) {
		//TODO: Clean up and make more streamline. (Think about the order of initialization)
		//Should things really be static?
		
		initTime = System.nanoTime();
		
		if(settings == null){
			settings = GameSettings.DEFAULT;
		}
		//TODO: Use the GameSettings
		
		setup(settings);
		
		
		if(settings.containsSetting("OnScreenDebug")){
			if(settings.getSettingAs("OnScreenDebug", Boolean.class)){
				basicDebug();
			}
		}else{
			Game.log.logWarning("No OnScreenDebug property in game settings");;
		}
		
		if(settings.containsSetting("DebugLog")){
			if(settings.getSettingAs("DebugLog", Boolean.class)){
				addDebugLog();
			}
		}else{
			Game.log.logWarning("No DebugLog property in game settings");;
		}
		
		if(settings.containsSetting("DebugID")){
			if(settings.getSettingAs("DebugID", Boolean.class)){
				addIDHandlerDebug();
			}
		}else{
			Game.log.logWarning("No DebugID property in game settings");;
		}
	}
	
	
	//TODO: Remove?
	/**
	 * 
	 */
	public Game() {
		initTime = System.nanoTime();

		basicSetup();
		
		basicDebug();
		
		
		completeSetup();
		
		addIDHandlerDebug();
	}
	
	private void setup(GameSettings settings){
		Game.game = this;
		
		physicsEngine = new PhysicsEngine(gameObjectHandler);
		
		name = GameSettings.DEFAULT.getSettingAs("Name", String.class);
		if(settings.containsSetting("Name")){
			name = settings.getSettingAs("Name", String.class);
		}
		
		Dimension res = GameSettings.DEFAULT.getSettingAs("Resolution", Dimension.class);
		if(settings.containsSetting("Resolution")){
			res = settings.getSettingAs("Resolution", Dimension.class);
		}
		
		int mode = GameSettings.DEFAULT.getSettingAs("ScreenMode", Integer.class);
		if(settings.containsSetting("ScreenMode")){
			mode = settings.getSettingAs("ScreenMode", Integer.class);
		}
		
		screen = new Screen(res, mode, name);
		
		if(settings.containsSetting("MainCamera")){
			camera = settings.getSettingAs("MainCamera", Camera.class);
		}

		if(camera == null){
			camera = GameSettings.DEFAULT.getSettingAs("MainCamera", Camera.class);
		}
		
		mouseHandler = new MouseInputHandler(gameObjectHandler, camera);
		keyHandler = new KeyInputHandler(gameObjectHandler);
		inputHandler = new Input(mouseHandler, keyHandler);
		
		screen.addPainter(camera);
		screen.addInputListener(inputHandler);

		AudioEngine.init(camera);
		
		gameObjectHandler.addGameObject(physicsEngine, "Physics Engine");
		
		gameObjectHandler.addGameObject(inputHandler, "Input Handler");
		
		gameObjectHandler.addGameObject(camera, "Main camera");
		
		if(settings.containsSetting("GameInit")){
			settings.getSettingAs("GameInit", GameInitializer.class).initialize(game, settings);
		}else{
			Game.log.log("No GameInit was found!! Exiting...", LogImportance.CRITICAL, "System", "Init", "Game");
			stop();
			System.exit(1);
		}
	}

	private void basicSetup() {
		Game.game = this;
		
		physicsEngine = new PhysicsEngine(gameObjectHandler);
		
		screen = new Screen(800, 600, ScreenManager.NORMAL, "Game");
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
		screen.setDebugEnabled(true);
		screen.addDebugText(() -> { return new String[]{
				"Frames: " + FPSCounter.framesTot,
				"Updates: " + UpdateCounter.updatesTot,
				"ElapsedTime (ns): " + elapsedTime,
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
	
	// TODO: Fix proper onStart onExit and other similar methods. (USE EVENTS!!)
	
	private void onQuit() {
		//TODO: These should be event driven
		if(IDDebug != null){
			IDDebug.stopDebug();
		}
		
		if(LogDebugFrame != null){
			LogDebugFrame.stopDebug();
		}
		
		eventMachine.fireEvent(new GameQuitEvent(this, name));
	}
	
	private void addDebugLog(){
		new Thread(LogDebugFrame = new LogDebugFrame(log), "Debug log").start();
	}
	
	private void addIDHandlerDebug() {
		new Thread(IDDebug = new IDHandlerDebugFrame<>(gameObjectHandler.getIDHandler()), "ID Handler Debug").start();
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
		startTime = System.nanoTime();
		currTime = startTime;
		elapsedTime = 0;
		
		log.logMessage("Pre run time: " + (startTime - initTime) / 1000000000f, "System");
		
		//FIXME: Allocating a lot of memory when updating many GameObjects

		log.logMessage("Running " + name + "!", "System");
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
	//NOTE: Is this needed? Probably not
	public static IDHandler<GameObject> getCurrentIDHandler() {
		return gameObjectHandler.getIDHandler(); //TODO: This is not needed.
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
