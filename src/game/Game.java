package game;

import java.awt.Dimension;
import java.util.function.Consumer;

import game.controller.event.EventMachine;
import game.controller.event.engineEvents.GameQuitEvent;
import game.controller.event.engineEvents.GameStartEvent;
import game.debug.IDHandlerDebugFrame;
import game.debug.log.Log;
import game.debug.log.Log.LogImportance;
import game.debug.log.frame.LogDebugFrame;
import game.gameObject.graphics.Camera;
import game.gameObject.handler.GameObjectHandler;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
import game.gameObject.physics.PhysicsEngine;
import game.input.Input;
import game.input.KeyInputHandler;
import game.input.MouseInputHandler;
import game.screen.Screen;
import game.sound.AudioEngine;
import game.util.FPSCounter;
import game.util.StandardUpdater;
import game.util.UpdateCounter;
import game.util.Updater;
import game.util.image.ImageUtils;
import game.util.math.MathUtils;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Game {
	
	//JAVADOC: Game
	
	//TODO: Restructure project packages to make more sense
	
	//NOTE: Should everything be static? There is only ever going to be one game... (or?)
	
	//TODO: Clean up
	
	//TODO: Clean up getters and setters, and maybe add shorthand methods for common uses (e.g logging and getting gameObjects)

	//TODO: Tasks and thread pooling for game and painting threads. (Support for other tasks too)
	//The game loop, graphics loop and potentially other systems should use a thread pool system
	//so that they can be more efficient. (The game update thread could be used for rendering if 
	//the rendering system was built to support that).
	
	//TODO: Find a good way to manage standard assets.
	//e.g the standard particle asset
	
	private static boolean running = false;
	private static boolean closeRequested = false;
	private static boolean paused = false;
	
	private static long startTime;
	private static long currTime;
	private static long elapsedTime;
	
	private int targetUPS;
	
	private boolean limitUPS = true;
	
	/**
	 * 
	 */
	public static long deltaTimeWarningThreshold = 100;
	
	private static float timeScale = 1;
	
	private static String name = "Game";

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
	
	private static GameSettings settings;

	private static PhysicsEngine physicsEngine;
	
	private static Camera camera; //TODO: This should support multiple cameras!

	/**
	 * 
	 */
	public static Screen screen; //FIXME: Support multiple screens.
	
	/**
	 * 
	 */
	public static MouseInputHandler mouseHandler;
	
	/**
	 * 
	 */
	public static KeyInputHandler keyHandler;
	
	/**
	 * 
	 */
	public static Updater updater;
	
	private static Input inputHandler;

	private static long initTime;
	
	//FIXME: Giant GC freeze
	//NOTE: The solution for now are the -XX:+UseG1GC -XX:MaxGCPauseMillis=50 VM flags that seem to have a satisfactory result.
	
	/**
	 * @param settings
	 */
	public Game(GameSettings settings) {
		//TODO: Clean up and make more streamline. (Think about the order of initialization)
		//Should things really be static?
		
		//TODO: Is this really doing anything?
		//The engine should be usable even though no hardware acceleration is present.
		System.setProperty("sun.java2d.opengl", "true");
		
		initTime = System.nanoTime();
		
		log.logMessage("Initializing the game!", "System", "Game");
		
		Game.settings = settings;
		
		if(settings == null){
			log.logWarning("No settings provided! Using the default settings!", "System", "Settings");
			settings = GameSettings.createDefaultGameSettings();
		}
		
		setup(settings);
		
		//NOTE: This should be done in setup
		
		if(settings.containsSetting("OnScreenDebug")){
			if(settings.getSettingAs("OnScreenDebug", Boolean.class)){
				basicDebug();
			}
		}else{
			Game.log.logWarning("No OnScreenDebug property in game settings!", "System", "Settings", "Debug");
		}
		
		if(settings.containsSetting("DebugLog")){
			if(settings.getSettingAs("DebugLog", Boolean.class)){
				new Thread(new LogDebugFrame(log), "Debug Log").start();
			}
		}else{
			Game.log.logWarning("No DebugLog property in game settings!", "System", "Settings", "Debug");
		}
		
		if(settings.containsSetting("DebugID")){
			if(settings.getSettingAs("DebugID", Boolean.class)){
				new Thread(new IDHandlerDebugFrame<>(gameObjectHandler.getIDHandler(), GameObjectCreatedEvent.class, GameObjectDestroyedEvent.class), "IDHandler Debug").start();
			}
		}else{
			Game.log.logWarning("No DebugID property in game settings!", "System", "Settings", "Debug");
		}
		
		if (settings.containsSetting("DebugGameSystem")) {
			if(settings.getSettingAs("DebugGameSystem", Boolean.class)){
				new Thread(new IDHandlerDebugFrame<>(GameSystem.getIDHandler()), "GameSystem Debug").start();
			}
		}else{
			Game.log.logWarning("No DebugGameSystem property in game settings!", "System", "Settings", "Debug");
		}
		
		
	}
	
	private void setup(GameSettings settings){
		final GameSettings DEFAULT = GameSettings.createDefaultGameSettings();
		
		Game.game = this;
		
		physicsEngine = new PhysicsEngine();
		
		name = DEFAULT.getSettingAs("Name", String.class);
		if(settings.containsSetting("Name")){
			name = settings.getSettingAs("Name", String.class);
		}else{
			log.logWarning("No 'Name' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		Dimension res = DEFAULT.getSettingAs("Resolution", Dimension.class);
		if(settings.containsSetting("Resolution")){
			res = settings.getSettingAs("Resolution", Dimension.class);
		}else{
			log.logWarning("No 'Resolution' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		Screen.Mode mode = DEFAULT.getSettingAs("ScreenMode", Screen.Mode.class);
		if(settings.containsSetting("ScreenMode")){
			mode = settings.getSettingAs("ScreenMode", Screen.Mode.class);
		}else{
			log.logWarning("No 'ScreenMode' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		int targetFPS = DEFAULT.getSettingAs("TargetFPS", Integer.class);
		if(settings.containsSetting("TargetFPS")){
			targetFPS = settings.getSettingAs("TargetFPS", Integer.class);
		}else{
			log.logWarning("No 'TargetFPS' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		screen = new Screen(res, mode, name, targetFPS);
		
		//NOTE: Should the camera be a part of the settings?
		camera = DEFAULT.getSettingAs("MainCamera", Camera.class);
		if(settings.containsSetting("MainCamera")){
			camera = settings.getSettingAs("MainCamera", Camera.class);
		}else{
			log.logWarning("No 'MainCamera' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		targetUPS = DEFAULT.getSettingAs("TargetUPS", Integer.class);
		if(settings.containsSetting("TargetFPS")){
			targetUPS = settings.getSettingAs("TargetUPS", Integer.class);
		}else{
			log.logWarning("No 'TargetUPS' settigns in settigns! Using the default setting.", "System", "Settings");
		}
		
		if(settings.containsSetting("Updater")){
			updater = settings.getSettingAs("Updater", Updater.class);
			log.logMessage("Found a custom updater!");
		}else{
			updater = new StandardUpdater();
		}
		
		//NOTE: Should we do this? Seems kind of weird, maybe have this as a setting?
		camera.setSize(screen.getWidth(), screen.getHeight());
		
		mouseHandler = new MouseInputHandler(camera);
		keyHandler = new KeyInputHandler();
		inputHandler = new Input(mouseHandler, keyHandler);
		
		//FIXME: Figure out if the separate lists of GameSystems in GameSystem and Updater is a problem.
		updater.addSystem(mouseHandler);
		
		if(settings.containsSetting("KeyBindings")){
			@SuppressWarnings("unchecked")
			Consumer<KeyInputHandler> keyBindings =  settings.getSettingAs("KeyBindings", Consumer.class);
			if(keyBindings != null){
				keyBindings.accept(keyHandler);
			}else{
				log.logWarning("Keybinding key found in settigns but the value is null!");
				//NOTE: Should there be a add default settings flag that adds the users keybindings onto of the standard ones.
			}
		}else{
			log.logMessage("Didn't find any keybindings in the settigns.");
		}
		
		screen.addPainter(camera);
		screen.addInputListener(inputHandler);

		AudioEngine.init(camera);
		
		updater.addSystem(physicsEngine);
		
		gameObjectHandler.addGameObject(camera, "Main camera");
		
		if(settings.containsSetting("GameInit")){
			settings.getSettingAs("GameInit", GameInitializer.class).initialize(game, settings);
		}else{
			Game.log.log("No GameInit was found!! Exiting...", LogImportance.CRITICAL, "System", "Init", "Game");
			stop();
			System.exit(1);
		}
	}
	
	//FIXME: This is a memory intensive solution, is there a better solution? 
	//NOTE: Does this really have any performance hit?
	private void basicDebug(){
		screen.setDebugEnabled(true);
		screen.addDebugText(() -> {
			return new String[]{
				"Frames: " + FPSCounter.framesTot,
				"Updates: " + UpdateCounter.updatesTot,
				"ElapsedTime (ns): " + elapsedTime,
				"Time: " + FPSCounter.timeTot,
				"FPS: " + FPSCounter.fps,
				"Average FPS: " + FPSCounter.averageFPS,
				"Frames dropped: " + Screen.framesDropped,
				"UPS: " + UpdateCounter.ups,
				"Average UPS: " + UpdateCounter.averageUPS,
				"Camera X: " + camera.getBounds().getX(),
				"Camera Y: " + camera.getBounds().getY(),
				"Objects: " + gameObjectHandler.numberOfGameObjects(),
				"Time scale: " + timeScale,
				"Image optimization calls: " + ImageUtils.calls,
				"Usefull image optimization calls: " + ImageUtils.usefullCalls,
				"Objects drawn: " + camera.drawnObjects
			};
		});
	}
	
	// TODO: Fix proper onStart onExit and other similar methods. (USE EVENTS!!)
	
	/**
	 * Starts the main loop of the game
	 */
	public void run() {
		Thread.currentThread().setName(name);
		
		log.logMessage("Starting...", "System");
		
		eventMachine.fireEvent(new GameStartEvent(this));

		// TODO: Make a nicer system for creating start threads
		new Thread(screen, "Graphics").start();
		startTime = System.nanoTime();
		currTime = startTime;
		elapsedTime = 0;
		
		log.logMessage("Pre run time: " + (startTime - initTime) / 1000000000f + "s", "System");
		
		long sleepTime = 0;
		long targetTime = 0;
		
		//FIXME: Allocating a lot of memory when updating many GameObjects

		log.logMessage("Running " + name + "!", "System");
		running = true;
		while (running) {
			long time = System.nanoTime();
			elapsedTime = time - currTime;
			currTime = time;

			if (closeRequested) {
				screen.stop();
				running = false;
			}
			
			//TODO: Some system for not rendering when paused. (Though some times that might be wanted behavior)
			//Note that when the threaded task system is implemented that that might be a good time to do that as
			//it will be a lot easier to do then. (Depends on implementation though)
			//Then this might be generalized to "not running specific tasks when paused" and possibly having special
			//behavior if needed.
			if (paused){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			gameObjectHandler.clearChange();
			
			updater.propagateUpdate((elapsedTime / 1000000000f) * timeScale);
			
			UpdateCounter.update(elapsedTime / 1000000000f);
			
			if((elapsedTime / 1000000f) > deltaTimeWarningThreshold){
				Game.log.logWarning("Game loop delta time exeeded " +
						Game.deltaTimeWarningThreshold + "ms with " + 
						((elapsedTime / 1000000f) - Game.deltaTimeWarningThreshold) + "ms",
						"Game", "DeltaTime", "System");
			}
			
			if(limitUPS){
				targetTime = (1000000000L / targetUPS);
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

		eventMachine.fireEvent(new GameQuitEvent(this));
		
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
	 * @param sceneInit
	 */
	public static void loadScene(GameInitializer sceneInit){
		pause();
		
		log.logMessage("Loading scene..");
		
		gameObjectHandler.clear();
		
		sceneInit.initialize(game, settings);
		
		log.logMessage("Loaded scene!");
		
		resume();
	}
	
	//TODO: Use either getters and setters or public variables to change variables in game
	
	/**
	 * @param name
	 */
	public void setName(String name){
		Game.name = name;
		screen.setTitle(name);
	}
	
	/**
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param timeScale
	 */
	public static void setTimeScale(float timeScale){
		Game.timeScale = MathUtils.clamp(timeScale, 0, 1000000000f);
	}
	
	/**
	 * @return
	 */
	public static float getTimeScale(){
		return timeScale;
	}
	
	/**
	 * @param limit
	 */
	public void limitUPS(boolean limit){
		this.limitUPS = limit;
	}
	
	/**
	 * @return
	 */
	public boolean isLimitingUPS(){
		return limitUPS;
	}
}
