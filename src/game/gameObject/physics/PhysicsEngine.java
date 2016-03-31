package game.gameObject.physics;

import game.Game;
import game.debug.logTool.LogMessage.LogImportance;
import game.gameObject.BasicGameObject;
import game.util.GameObjectHandler;
import game.util.UpdateListener;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author Julius Häger
 */
public class PhysicsEngine extends BasicGameObject implements UpdateListener {

	// JAVADOC: PhysicsEngine

	// TODO: PhysicsEngine

	private GameObjectHandler gameObjectHandeler;
	
	private HashMap<Integer, CopyOnWriteArrayList<Collidable>> collidablesMap = new HashMap<>();

	//TODO: Use the HashMap instead.
	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	/**
	 * @param gameObjectHandeler
	 */
	public PhysicsEngine(GameObjectHandler gameObjectHandeler) {
		super(0, 0, 0, 0, 0);
		this.gameObjectHandeler = gameObjectHandeler;
		
		Game.log.logMessage("PhysicsEngine created", "Physics");
	}
	
	CopyOnWriteArrayList<Collidable> tempList;
	
	@Override
	public void update(long timeMillis) {
		if (gameObjectHandeler.haveObjectsChanged()) {
			//Game.log.logMessage("PhysicsEngine objects changed", "Physics");
			collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();
			for (int z : gameObjectHandeler.getZLevels()) {
				tempList = gameObjectHandeler.getAllGameObjectsAtZLevelExtending(z, Collidable.class);
				if(tempList.size() > 1){
					collidables.add(tempList);
				}
			}
			if(collidables.size() < 2){
				return;
			}
		}

		for (CopyOnWriteArrayList<Collidable> collidablesInLayer : collidables) {
			for (int c1 = 0; c1 < collidablesInLayer.size(); c1++) {
				for (int c2 = c1 + 1; c2 < collidablesInLayer.size(); c2++) {
					if (c1 != c2) {
						if (collidablesInLayer.get(c1).getBounds()
								.intersects((Rectangle2D) collidablesInLayer.get(c2).getBounds())) {
							//Game.log.log("Collition!", LogImportance.DEBUG, "Physics");
							collidablesInLayer.get(c1).hasCollided(collidablesInLayer.get(c2));
						}
					}
				}
			}
		}
	}
}
