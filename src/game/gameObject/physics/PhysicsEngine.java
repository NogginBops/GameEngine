package game.gameObject.physics;

import game.util.GameObjectHandler;
import game.util.UpdateListener;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author Julius Häger
 */
public class PhysicsEngine implements UpdateListener {

	// JAVADOC: PhysicsEngine

	// TODO: PhysicsEngine

	private GameObjectHandler gameObjectHandeler;

	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>> collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();

	/**
	 * @param gameObjectHandeler
	 */
	public PhysicsEngine(GameObjectHandler gameObjectHandeler) {
		this.gameObjectHandeler = gameObjectHandeler;
	}

	@Override
	public void update(long timeMillis) {
		// int checks = 0;
		if (gameObjectHandeler.haveObjectsChanged()) {
			// long startTime = System.nanoTime();
			collidables = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collidable>>();
			for (int z : gameObjectHandeler.getZLevels()) {
				collidables.add(gameObjectHandeler.getAllGameObjectsAtZLevelGameObjectExtending(z, Collidable.class));
			}
			// long endTime = System.nanoTime();
			// System.out.println("Fetching gameObjects: " + (endTime -
			// startTime)/1000000000f);
		}

		// long startTime = System.nanoTime();
		for (CopyOnWriteArrayList<Collidable> collidablesInLayer : collidables) {
			for (int c1 = 0; c1 < collidablesInLayer.size(); c1++) {
				for (int c2 = c1 + 1; c2 < collidablesInLayer.size(); c2++) {
					if (c1 != c2) {
						if (collidablesInLayer.get(c1).getBounds()
								.intersects((Rectangle2D) collidablesInLayer.get(c2).getBounds())) {
							collidablesInLayer.get(c1).hasCollided(collidablesInLayer.get(c2));
						}
					}
					// checks++;
				}
			}
		}
		// long endTime = System.nanoTime();
		// System.out.println("Collition check: " + (endTime -
		// startTime)/1000000000f);

		// System.out.println("PhysicsEngine.update() made " + checks + "
		// collition checks");
	}
}
