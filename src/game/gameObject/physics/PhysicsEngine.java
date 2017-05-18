package game.gameObject.physics;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.GameSystem;
import game.debug.DebugOutputProvider;

/**
 * 
 * @author Julius Häger
 */
public class PhysicsEngine extends GameSystem implements DebugOutputProvider {

	// JAVADOC: PhysicsEngine

	//NOTE: How should physics be implemented to facilitate good multi-threading?
	
	// TODO: PhysicsEngine
	
	// TODO: Implement a grid system for efficient collision checking.
	//Could be hard as the Collidable itself does not know what position in the grid it has.
	//Could a method getGrid() and some cache variable that might update depending on movement
	//be the solution?
	
	//TODO: Explore if this solution is better than the one currently in use
	private HashMap<Integer, CopyOnWriteArrayList<Collidable>> collidablesMap = new HashMap<Integer, CopyOnWriteArrayList<Collidable>>();

	//TODO: Explore the HashMap solution.
	//private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	private final boolean debug = true;
	
	/**
	 * @param gameObjectHandeler
	 */
	public PhysicsEngine() {
		super("Physics Engine");
		Game.log.logMessage("PhysicsEngine created", "Physics"); //Is this really needed?
		/*
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, (event) -> {
			if (event.object instanceof Collidable) {
				int layer = ((Collidable)event.object).getPhysicsLayer();
				if (collidablesMap.containsKey(layer) == false) {
					collidablesMap.put(layer, new CopyOnWriteArrayList<>());
				}
				
				collidablesMap.get(layer).add((Collidable) event.object);
			}
		});
		
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, (event) -> {
			if (event.object instanceof Collidable) {
				int layer = ((Collidable)event.object).getPhysicsLayer();
				if (collidablesMap.containsKey(layer)) {
					collidablesMap.get(layer).remove(event.object);
				}
			}
		});
		*/
	}
	
	CopyOnWriteArrayList<Collidable> tempList;
	
	float timer = 0;
	
	int loopCount = 0;
	
	int oldLoopCount = 0;
	
	int objects = 0;
	
	@Override
	public void earlyUpdate(float deltaTime) {
		
	}

	@Override
	public void lateUpdate(float deltaTime) {
		//NOTE: Use events for Collidables?
		if (Game.gameObjectHandler.shouldUpdateObjects()) {
			
			collidablesMap.clear();
			
			CopyOnWriteArrayList<Collidable> allCollidables = Game.gameObjectHandler.getAllActiveGameObjectsExtending(Collidable.class);
			
			if(allCollidables.size() <= 1){
				return;
			}
			
			for (Collidable collidable : allCollidables) {
				int physicsLayer = collidable.getPhysicsLayer();
				if(collidablesMap.containsKey(physicsLayer) == false){
					collidablesMap.put(physicsLayer, new CopyOnWriteArrayList<>());
				}
				
				collidablesMap.get(physicsLayer).add(collidable);
			}
			
			/*
			collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();
			for (int z : Game.gameObjectHandler.getZLevels()) {
				tempList = Game.gameObjectHandler.getAllGameObjectsAtZLevelExtending(z, Collidable.class);
				if(tempList.size() > 1){
					collidables.add(tempList);
				}
			}
			if(collidables.size() < 1){
				return;
			}*/
		}
		
		
		
		for (CopyOnWriteArrayList<Collidable> collidablesInLayer : collidablesMap.values()) {
			if(collidablesInLayer.size() <= 1){
				continue;
			}
			
			for (int c1 = 0; c1 < collidablesInLayer.size(); c1++) {
				for (int c2 = c1 + 1; c2 < collidablesInLayer.size(); c2++) {
					loopCount++;
					
					if(collidablesInLayer.get(c1).isActive() && collidablesInLayer.get(c2).isActive()){
						if(collidablesInLayer.get(c1).getBounds().intersects(collidablesInLayer.get(c2).getBounds())){
							if(collides(collidablesInLayer.get(c1).getCollitionShape(), collidablesInLayer.get(c2).getCollitionShape())){
								collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
							}
						}
					}
				}
			}
		}
		
		/*
		for (CopyOnWriteArrayList<Collidable> collidablesInLayer : collidables) {
			for (int c1 = 0; c1 < collidablesInLayer.size(); c1++) {
				for (int c2 = c1 + 1; c2 < collidablesInLayer.size(); c2++) {
					loopCount++;
					
					if(collidablesInLayer.get(c1).isActive() && collidablesInLayer.get(c2).isActive()){
						if(collidablesInLayer.get(c1).getBounds().intersects(collidablesInLayer.get(c2).getBounds())){
							if(collides(collidablesInLayer.get(c1).getCollitionShape(), collidablesInLayer.get(c2).getCollitionShape())){
								collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
							}
						}
					}
				}
			}
		}
		*/	
		if(debug){
			timer -= deltaTime;
			if(timer <= 0){
				timer = 2;
				
				objects = 0;
				for (CopyOnWriteArrayList<Collidable> copyOnWriteArrayList : collidablesMap.values()) {
					objects += copyOnWriteArrayList.size();
				}
				
				//Game.log.logDebug("Loop count: " + loopCount + " for "  + collidablesMap.keySet().size() + " layers and " + objects + " objects!");
			}
			
			oldLoopCount = loopCount;
			
			loopCount = 0;
		}
	}
	
	
	private void collide(Collidable c1, Collidable c2){
		c1.hasCollided(c2);
		c2.hasCollided(c1);
	}
	
	//FIXME: collies has a lot of self time
	// This method might not be needed when a better system is implemented.
	
	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean collides(Shape s1, Shape s2){
		//TODO: Is this even needed?
		// Polymorphism should handle this?
		
		if(s1 instanceof Rectangle2D && s2 instanceof Rectangle2D){
			if(((Rectangle2D)s1).intersects((Rectangle2D)s2)){
				return true;
			}
		}
		else if(s1 instanceof Rectangle2D){
			//NOTE: Is this accurate enough?
			if(s2.intersects((Rectangle2D)s1)){
				return true;
			}
		}
		else if(s2 instanceof Rectangle2D){
			if(s1.intersects((Rectangle2D)s2)){
				return true;
			}
		}
		else{
			//We are doing this because you can't intersect two generic shapes.
			//TODO: Is there a better way to do this?
			Area intersection = new Area(s1);
			intersection.intersect(new Area(s2));
			
			if(!intersection.isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param shape
	 * @param layers
	 * @return
	 */
	public Collidable[] overlapShape(Shape shape, int ... layers){
		ArrayList<Collidable> collisions = new ArrayList<>();
		
		for (int layer : layers) {
			if (collidablesMap.containsKey(layer)) {
				for (Collidable collidable : collidablesMap.get(layer)) {
					if (collides(shape, collidable.getCollitionShape())) {
						collisions.add(collidable);
					}
				}
			}
		}
		
		return collisions.toArray(new Collidable[collisions.size()]);
	}

	@Override
	public String[] getDebugValues() {
		if (debug) {
			return new String[]{ 
					"<b>Objects: </b>" + objects,
					"<b>Layers: </b>" + collidablesMap.keySet().size(),
					"<b>LoopCount: </b>" + oldLoopCount 
					};
		}else{
			return new String[0];
		}
	}
}
