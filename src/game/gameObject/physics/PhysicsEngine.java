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

	// TODO: PhysicsEngine
	
	// TODO: Physics layers
	
	// TODO: Implement a grid system for efficient collision checking.
	//Could be hard as the Collidable itself does not know what position in the grid it has.
	//Could a method getGrid() and some cache variable that might update depending on movement
	//be the solution?
	
	//TODO: Explore if this solution is better than the one currently in use
	@SuppressWarnings("unused")
	private HashMap<Integer, CopyOnWriteArrayList<Collidable>> collidablesMap = new HashMap<Integer, CopyOnWriteArrayList<Collidable>>();

	//TODO: Explore the HashMap solution.
	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	private boolean debug = false;
	
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
	public void update(long timeNano) {
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
					//Unneeded check
					if (c1 != c2) {
						if(collidablesInLayer.get(c1).isActive() && collidablesInLayer.get(c2).isActive()){
							
							//TODO: Optimize collition checking
							Shape collitionShape1 = collidablesInLayer.get(c1).getCollitionShape();
							Shape collitionShape2 = collidablesInLayer.get(c2).getCollitionShape();
							
							if(collitionShape1 instanceof Rectangle2D && collitionShape2 instanceof Rectangle2D){
								if(((Rectangle2D)collitionShape1).intersects((Rectangle2D)collitionShape2)){
									collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
								}
							}
							else if(collitionShape1 instanceof Rectangle2D){
								//NOTE: Is this accurate enough?
								if(collitionShape2.intersects((Rectangle2D)collitionShape1)){
									collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
								}
							}
							else if(collitionShape2 instanceof Rectangle2D){
								if(collitionShape1.intersects((Rectangle2D)collitionShape2)){
									collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
								}
							}
							else{
								Area intersection = new Area(collitionShape1);
								intersection.intersect(new Area(collitionShape2));
								
								if(!intersection.isEmpty()){
									collide(collidablesInLayer.get(c1), collidablesInLayer.get(c2));
								}
							}
							
							/*Area collition = collidablesInLayer.get(c1).getCollitionArea();
							collition.intersect(collidablesInLayer.get(c2).getCollitionArea());
							if (!collition.isEmpty()) {
								collidablesInLayer.get(c1).hasCollided(collidablesInLayer.get(c2));
								collidablesInLayer.get(c2).hasCollided(collidablesInLayer.get(c1));
							}*/
						}
					}
				}
			}
		}
		
		timer -= timeNano/1000000000f;
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
}
