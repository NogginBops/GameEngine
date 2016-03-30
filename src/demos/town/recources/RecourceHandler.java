package demos.town.recources;

import java.util.ArrayList;

import game.gameObject.BasicGameObject;
import game.util.UpdateListener;

/**
 * @author Julius Häger
 *
 */
public class RecourceHandler extends BasicGameObject implements UpdateListener {

	private ArrayList<RecourcePool> pools;

	private ArrayList<RecourceGenerator> generators;

	/**
	 * 
	 */
	public RecourceHandler() {
		super(0, 0, 0, 0, 0);
		pools = new ArrayList<>();
		generators = new ArrayList<>();
	}

	@Override
	public void update(long timeNano) {
		float time = timeNano / 1000000000f;
		float carry = 0;
		for (RecourceGenerator generator : generators) {
			for (RecourcePool pool : pools) {
				carry = pool.addRecources(generator.genRate() * time + carry);
				if (carry == 0) {
					break;
				}
			}
		}
		// TODO: Add to game metrics
	}

	/**
	 * @param pool
	 */
	public void addRecourcePool(RecourcePool pool) {
		pools.add(pool);
	}

	/**
	 * @param pool
	 */
	public void removeRecourcePool(RecourcePool pool) {
		pools.remove(pool);
	}

	/**
	 * @param generator
	 */
	public void addRecourceGenerator(RecourceGenerator generator) {
		generators.add(generator);
	}

	/**
	 * @param generator
	 */
	public void removeRecourceGenerator(RecourceGenerator generator) {
		generators.remove(generator);
	}

	/**
	 * @return
	 */
	public float getCurrentAmount() {
		float amount = 0;
		for (RecourcePool pool : pools) {
			amount += pool.getCurrentCapacity();
		}
		return amount;
	}

	/**
	 * @param amount
	 * @return
	 */
	public float useRecources(float amount) {
		for (RecourcePool pool : pools) {
			if (amount == (amount = pool.useRecource(amount))) {
				break;
			}
		}
		return amount;
	}
}
