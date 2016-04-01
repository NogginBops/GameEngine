package game.util;

import game.gameObject.GameObject;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

	private ConcurrentSkipListMap<Integer, CopyOnWriteArrayList<GameObject>> gameObjectMap;
	
	//FIXME: Remove gameObjects list.
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
		/*
		 * CopyOnWriteArrayList<GameObject> objects =
		 * gameObjectMap.get(gameObject.getZOrder()); if(objects == null){
		 * gameObjectMap.put(gameObject.getZOrder(), objects = new
		 * CopyOnWriteArrayList<GameObject>()); } objects.add(gameObject);
		 * 
		 * //TODO: Remove gameObjects.add(gameObject); gameObjects.sort(null);
		 * if(!zLevels.contains(gameObject.getZOrder())){
		 * zLevels.add(gameObject.getZOrder()); zLevels.sort(null); }
		 * idHandler.addObject(gameObject); objectsChanged = true;
		 */
	}

	/**
	 * 
	 * @param gameObject
	 * @param nameID
	 */
	public <T extends GameObject> void addGameObject(T gameObject, String nameID) {
		addGameObject(new ID<GameObject>(nameID, 0, gameObject));
		/*
		 * CopyOnWriteArrayList<GameObject> objects =
		 * gameObjectMap.get(gameObject.getZOrder()); if(objects == null){
		 * gameObjectMap.put(gameObject.getZOrder(), objects = new
		 * CopyOnWriteArrayList<GameObject>()); } objects.add(gameObject);
		 * 
		 * //TODO: Remove gameObjects.add(gameObject); gameObjects.sort(null);
		 * if(!zLevels.contains(gameObject.getZOrder())){
		 * zLevels.add(gameObject.getZOrder()); zLevels.sort(null); }
		 * idHandler.addObject(gameObject, nameID); objectsChanged = true;
		 */
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

		// TODO: Remove
		gameObjects.remove(gameObject);
		if (!zLevels.contains(gameObject.getZOrder())) {
			zLevels.remove(gameObject.getZOrder());
		}
		idHandler.removeObject(gameObject);
		objectsChanged = true;
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
	}

	/**
	 * 
	 * @return
	 */
	public CopyOnWriteArrayList<Integer> getZLevels() {
		return zLevels;
	}

	/**
	 * @return
	 */
	public CopyOnWriteArrayList<GameObject> getAllGameObjects() {
		return gameObjects;
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
		// System.out.println("Called getAllGameObjectsExtending() with
		// arguments: " + classT);
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : gameObjects) {
			if (classT.isAssignableFrom(object.getClass())) {
				returnList.add(classT.cast(object));
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
		// System.out.println("Called getAllGameObjectsAtZLevel() with
		// arguments: " + zLevel);
		return gameObjectMap.get(zLevel);
	}

	/**
	 * 
	 * @param zLevel
	 * @param classT
	 * @return
	 */
	public <T extends GameObject> CopyOnWriteArrayList<T> getAllGameObjectsAtZLevelExtending(int zLevel,
			Class<T> classT) {
		// System.out.println("Called
		// getAllGameObjectsAtZLevelGameObjectExtending() with arguments: " +
		// zLevel + " and " + classT);
		CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<T>();
		for (GameObject object : getAllGameObjectsAtZLevel(zLevel)) {
			if (classT.isAssignableFrom(object.getClass())) {
				returnList.add(classT.cast(object));
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
}
