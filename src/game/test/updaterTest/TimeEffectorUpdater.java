package game.test.updaterTest;

import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.gameObject.physics.Collidable;
import game.gameObject.physics.PhysicsEngine;
import game.util.UpdateListener;
import game.util.Updater;

/**
 * @author Julius Häger
 *
 */
public class TimeEffectorUpdater extends Updater {

	protected CopyOnWriteArrayList<TimeEffector> timeEffectors;
	
	/**
	 * 
	 */
	public TimeEffectorUpdater() {
		super();
		timeEffectors = new CopyOnWriteArrayList<>();
	}

	@Override
	public void propagateUpdate(float deltaTime) {
		if(Game.gameObjectHandler.shouldUpdateObjects()){
			listeners = Game.gameObjectHandler.getAllActiveGameObjectsExtending(UpdateListener.class);
			timeEffectors = Game.gameObjectHandler.getAllActiveGameObjectsExtending(TimeEffector.class);
		}
		
		for (UpdateListener listener : listeners) {
			if(listener.isActive()){
				float timeMult = 1;
				
				//NOTE: This won't work for big particle systems that move the emitters and effectors because
				//the multiplier is calculated for the whole system
				
				//Should there be an option to exclude the multiplier on certain classes
				
				//Calculate the local time multiplier
				for (TimeEffector timeEffector : timeEffectors) {
					if(listener instanceof Collidable){
						if(PhysicsEngine.collides(((Collidable)listener).getCollitionShape(), timeEffector.getCollitionShape())){
							timeMult *= timeEffector.getTimeMultiplier();
						}
					}else{
						if(PhysicsEngine.collides(listener.getShape(), timeEffector.getCollitionShape())){
							timeMult *= timeEffector.getTimeMultiplier();
						}
					}
				}
				
				//Apply time
				listener.update(deltaTime * timeMult);
			}
		}
	}

}
