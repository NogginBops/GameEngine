package game.gameObject.handler;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.gameObject.GameObject;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
import game.util.ID;
import game.util.IDHandler;

/**
 * <p>
 * The GameObjectHandeler is a class responsible for storing and receiving all
 * the {@link GameObject GameObjects} in a scene. <br>
 * In short you can say the GameObjectHandeler is the Games scene.
 * </p>
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class GameObjectHandler {

	// JAVADOC: GameObjectHandeler
	
	//TODO: Add listeners to specific types of gameObjects so that they get notified when a object is added/removed so that they can optimize adding and removing objects.
	//Should this be done on the caller thread or should a worker thread be used? What would work best.
	//Another way to do this is to register types of gameObject to pool for fast retrieval. But this still requires a whole new list to be passed.

	private ConcurrentSkipListMap<Integer, CopyOnWriteArrayList<GameObject>> gameObjectMap;
	
	/*
	 * NOTE: 
	 * This list might not be needed but it is kept as convenience
	 * as some methods can not use the SkipListMap effectively due to the
	 * map not being able to quickly list all of the keys or values contained in it.
	 */
	private CopyOnWriteArrayList<GameObject> gameObjects;

	private CopyOnWriteArrayList<Integer> zLevels;

	private IDHandler<GameObject> idHandler;

	private boolean shouldUpdateObjects = true;
	
	private boolean objectsChanged = false;

	/**
	 * 
	 */
	public GameObjectHandler() {
		gameObjectMap = new ConcurrentSkipListMap<Integer, CopyOnWriteArrayList<GameObject>>();
		gameObjects = new CopyOnWriteArrayList<GameObject>();
		zLevels = new CopyOnWriteArrayList<Integer>();
		idHandler = new IDHandler<GameObject>();
	}

	/**
	 * 
	 * @param gameObject
	 */
	public <T extends GameObject> void addGameObject(T gameObject) {
		addGameObject(gameObject, gameObject.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param gameObject
	 * @param nameID
	 */
	public <T extends GameObject> void addGameObject(T gameObject, String nameID) {
		//idHandler.getLastID() + 1  ---  Pretty good guess for a unused id.
		addGameObject(new ID<GameObject>(nameID, idHandler.getLastID() + 1, gameObject));
	}

	/**
	 * 
	 * @param id
	 */
	public <T extends GameObject> void addGameObject(ID<GameObject> id) {
		CopyOnWriteArrayList<GameObject> objects = gameObjectMap.get(id.object.getZOrder());
		if (objects == null) {
			gameObjectMap.put(id.object.getZOrder(), objects = new CopyOnWriteArrayList<GameObject>());
		}
		objects.add(id.object);

		gameObjects.add(id.object);
		gameObjects.sort(null);
		if (!zLevels.contains(id.object.getZOrder())) {
			zLevels.add(id.object.getZOrder());
			zLevels.sort(null);
		}
		idHandler.addID(id);
		objectsChanged = true;
		
		Game.eventMachine.fireEvent(new GameObjectCreatedEvent(this, id.object));
	}
	
	//TODO: Clean up add and remove methods.

	/**
	 * 
	 * @param gameObject
	 */
	public void removeGameObject(GameObject gameObject) {
		CopyOnWriteArrayList<GameObject> objects = gameObjectMap.get(gameObject.getZOrder());
		objects.remove(gameObject);
		if (objects.size() == 0) {
			gameObjectMap.remove(gameObject.getZOrder());
		}

		// TODO: Remove?
		gameObjects.remove(gameObject);
		if (!zLevels.contains(gameObject.getZOrder())) {
			zLevels.remove(gameObject.getZOrder());
		}
		idHandler.removeObject(gameObject);
		objectsChanged = true;
		
		Game.eventMachine.fireEvent(new GameObjectDestroyedEvent(this, gameObject));
	}

	/**
	 * 
	 * @param id
	 */
	public void removeGameObject(ID<GameObject> id) {
		gameObjectMap.get(id.object.getZOrder()).remove(id.object);
		if (gameObjectMap.get(id.object.getZOrder()).size() == 0) {
			gameObjectMap.remove(id.object.getZOrder());
		}
		
		gameObjects.remove(id.object);
		if (!zLevels.contains(id.object.getZOrder())) {
			zLevels.remove(id.object.getZOrder());
		}
		idHandler.removeID(id);
		objectsChanged = true;
		
		Game.eventMachine.fireEvent(new GameObjectDestroyedEvent(this, id.object));
	}

	/**
	 * 
	 * @return
	 */
	public CopyOnWriteArrayList<Integer> getZLevels() {
		//TODO: Try make this not allocate memory
		return new CopyOnWriteArrayList<>(gameObjectMap.keySet());
	}

	/**
	 * @return
	 */
	public CopyOnWriteArrayList<GameObject> getAllGameObjects() {
		return gameObjects;
	}
	
	//TODO: Maybe implement updateListener to be able to figure out when the number of active GameObjects change.
	//Is this a performance update or is it slower than the current system?
	
	/**
	 * @return
	 */
	public CopyOnWriteArrayList<GameObject> getAllActiveGameObjects() {
		CopyOnWriteArrayList<GameObject> returnList = new CopyOnWriteArrayList<>();
		for (GameObject gameObject : gameObjects) {
			if(gameObject.isActive()){
				returnList.add(gameObject);
			}
		}
		return returnList;
	}

	/**
	 * This method finds all the registered {@link GameObject GameObjects}
	 * extending a certain subclass T.
	 * 
	 * @param classT
	 *            The requested {@link Class}
	 * @return A ArratList of all the {@link GameObject GameObjects} extending
	 *         the class T.
	 */
	public <T extends GameObject> CopyOnWriteArrayList<T> getAllGameObjectsExtending(Class<T> classT) {
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : gameObjects) {
			if (classT.isAssignableFrom(object.getClass())) {
				returnList.add(classT.cast(object));
			}
		}
		return returnList;
	}
	
	/**
	 * This method finds all the registered {@link GameObject GameObjects} 
	 * extending a certain subclass T 
	 * whose {@link GameObject#isActive() isActive()} method returns true.
	 * 
	 * @param classT
	 *            The requested {@link Class}
	 * @return A ArratList of all the {@link GameObject GameObjects} extending
	 *         the class T.
	 */
	public <T extends GameObject> CopyOnWriteArrayList<T> getAllActiveGameObjectsExtending(Class<T> classT) {
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : gameObjects) {
			if(object.isActive()){
				if (classT.isAssignableFrom(object.getClass())) {
					returnList.add(classT.cast(object));
				}
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param zLevel
	 * @return
	 */
	public CopyOnWriteArrayList<GameObject> getAllGameObjectsAtZLevel(int zLevel) {
		return gameObjectMap.get(zLevel);
	}
	
	/**
	 * 
	 * @param zLevel
	 * @return
	 */
	public CopyOnWriteArrayList<GameObject> getAllActiveGameObjectsAtZLevel(int zLevel) {
		CopyOnWriteArrayList<GameObject> returnList = new CopyOnWriteArrayList<>();
		for (GameObject gameObject : gameObjectMap.get(zLevel)) {
			if(gameObject.isActive()){
				returnList.add(gameObject);
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param zLevel
	 * @param classT
	 * @return
	 */
	public <T extends GameObject> CopyOnWriteArrayList<T> getAllGameObjectsAtZLevelExtending(int zLevel, Class<T> classT) {
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : gameObjectMap.get(zLevel)) {
			if (classT.isAssignableFrom(object.getClass())) {
				returnList.add(classT.cast(object));
			}
		}
		return returnList;
	}
	
	/**
	 * 
	 * @param zLevel
	 * @param classT
	 * @return
	 */
	public <T extends GameObject> CopyOnWriteArrayList<T> getAllActiveGameObjectsAtZLevelExtending(int zLevel, Class<T> classT) {
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : gameObjectMap.get(zLevel)) {
			if(object.isActive()){
				if (classT.isAssignableFrom(object.getClass())) {
					returnList.add(classT.cast(object));
				}
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param nameID
	 * @return
	 */
	public GameObject getGameObjectByNameID(String nameID) {
		return idHandler.getObject(nameID);
	}

	/**
	 * 
	 * @return
	 */
	public int numberOfGameObjects() {
		return gameObjects.size();
	}

	/**
	 * 
	 * @return
	 */
	public boolean shouldUpdateObjects() {
		return shouldUpdateObjects;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean haveObjectsChanged(){
		return objectsChanged;
	}

	/**
	 * 
	 */
	public void clearChange() {
		shouldUpdateObjects = objectsChanged;
		objectsChanged = false;
	}

	/**
	 * 
	 * @return
	 */
	public IDHandler<GameObject> getIDHandler() {
		return idHandler;
	}

	/**
	 * 
	 */
	public void clear() {
		for (GameObject gameObject : getAllGameObjects()) {
			removeGameObject(gameObject);
		}
	}
}
