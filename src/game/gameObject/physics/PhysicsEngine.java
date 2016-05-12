package game.gameObject.physics;

import java.awt.geom.Area;
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
	
	//TODO: Explore if this solution is better than the one currently in use
	@SuppressWarnings("unused")
	private HashMap<Integer, CopyOnWriteArrayList<Collidable>> collidablesMap = new HashMap<Integer, CopyOnWriteArrayList<Collidable>>();

	//TODO: Explore the HashMap solution.
	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	/**
	 * @param gameObjectHandeler
	 */
	public PhysicsEngine(GameObjectHandler gameObjectHandeler) {
		super(0, 0, 0, 0, 0);
		
		Game.log.logMessage("PhysicsEngine created", "Physics");
	}
	
	CopyOnWriteArrayList<Collidable> tempList;
	
	@Override
	public void update(long timeMillis) {
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
					if (c1 != c2) {
						if(collidablesInLayer.get(c1).isActive() && collidablesInLayer.get(c2).isActive()){
							Area collition = collidablesInLayer.get(c1).getCollitionArea();
							collition.intersect(collidablesInLayer.get(c2).getCollitionArea());
							if (!collition.isEmpty()) {
								collidablesInLayer.get(c1).hasCollided(collidablesInLayer.get(c2));
								collidablesInLayer.get(c2).hasCollided(collidablesInLayer.get(c1));
							}
						}
					}
				}
			}
		}
	}
}
