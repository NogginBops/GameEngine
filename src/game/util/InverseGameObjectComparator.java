package game.util;

import game.gameObject.GameObject;

import java.util.Comparator;

/**
 * This {@link Comparator} is used to sort {@link GameObject GameObjects} in
 * reverse Z-order.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class InverseGameObjectComparator implements Comparator<GameObject> {

	@Override
	public int compare(GameObject o1, GameObject o2) {
		return o1.getZOrder() > o2.getZOrder() ? -1 : (o1.getZOrder() == o2.getZOrder()) ? 0 : 1;
	}
}
