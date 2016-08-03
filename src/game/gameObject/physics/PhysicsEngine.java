package game.gameObject.physics;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.gameObject.BasicGameObject;
import game.gameObject.handler.GameObjectHandler;
import game.util.UpdateListener;

/**
 * 
 * @author Julius Häger
 */
public class PhysicsEngine extends BasicGameObject implements UpdateListener {

	// JAVADOC: PhysicsEngine

	//FIXME: Make the physics and collision into a GameSystem!
	//NOTE: How should physics be implemented to facilitate good multi-threading?
	
	// TODO: PhysicsEngine
	
	// FIXME: Physics layers
	//This would allow gameobjects to collide with each other while not being on the same z layer
	//NOTE: Should the z layer property be a part of the paintable interface? It is to note that
	// the mouse input is also dependent on the z layer of a gameobject.
	
	// TODO: Implement a grid system for efficient collision checking.
	//Could be hard as the Collidable itself does not know what position in the grid it has.
	//Could a method getGrid() and some cache variable that might update depending on movement
	//be the solution?
	
	//TODO: Explore if this solution is better than the one currently in use
	@SuppressWarnings("unused")
	private HashMap<Integer, CopyOnWriteArrayList<Collidable>> collidablesMap = new HashMap<Integer, CopyOnWriteArrayList<Collidable>>();

	//TODO: Explore the HashMap solution.
	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	private boolean debug = true;
	
	/**
	 * @param gameObjectHandeler
	 */
	public PhysicsEngine(GameObjectHandler gameObjectHandeler) {
		super(0, 0, 0, 0, 0);
		
		Game.log.logMessage("PhysicsEngine created", "Physics");
	}
	
	CopyOnWriteArrayList<Collidable> tempList;
	
	float timer = 0;
	
	int loopCount = 0;
	
	@Override
	public void update(float deltaTime) {
		if (Game.gameObjectHandler.shouldUpdateObjects()) {
			collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();
			for (int z : Game.gameObjectHandler.getZLevels()) {
				tempList = Game.gameObjectHandler.getAllGameObjectsAtZLevelExtending(z, Collidable.class);
				if(tempList.size() > 1){
					collidables.add(tempList);
				}
			}
			if(collidables.size() < 1){
				return;
			}
		}
		
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
		
		timer -= deltaTime;
		if(timer <= 0){
			timer = 2;
			
			int objects = 0;
			for (CopyOnWriteArrayList<Collidable> copyOnWriteArrayList : collidables) {
				objects += copyOnWriteArrayList.size();
			}
			
			if(debug){
				Game.log.logDebug("Loop count: " + loopCount + " for "  + collidables.size() + " layers and " + objects + " objects!");
			}
		}
		
		loopCount = 0;
	}
	
	private void collide(Collidable c1, Collidable c2){
		c1.hasCollided(c2);
		c2.hasCollided(c1);
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean collides(Shape s1, Shape s2){
		//NOTE: We are doing this because you can't intersect two generic shapes.
		//There 
		if(s1 instanceof Rectangle2D && s2 instanceof Rectangle2D){
			if(((Rectangle2D)s1).intersects((Rectangle2D)s2)){
				return true;
				//collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
			}
		}
		else if(s1 instanceof Rectangle2D){
			//NOTE: Is this accurate enough?
			if(s2.intersects((Rectangle2D)s1)){
				return true;
				//collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
			}
		}
		else if(s2 instanceof Rectangle2D){
			if(s1.intersects((Rectangle2D)s2)){
				return true;
				//collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
			}
		}
		else{
			Area intersection = new Area(s1);
			intersection.intersect(new Area(s2));
			
			if(!intersection.isEmpty()){
				return true;
				//collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
			}
		}
		return false;
	}
}
