package town;

import town.recources.RecourceHandler;

/**
 * @author Julius Häger
 *
 */
public class GameMaster {
	
	private static RecourceHandler money, metal, electricity;

	/**
	 * 
	 */
	public GameMaster() {
		money = new RecourceHandler();
		metal = new RecourceHandler();
		electricity = new RecourceHandler();
	}

	/**
	 * @return
	 */
	public static RecourceHandler getMoneyHandler() {
		return money;
	}

	/**
	 * @return
	 */
	public static RecourceHandler getMetalHandler() {
		return metal;
	}

	/**
	 * @return
	 */
	public static RecourceHandler getElectricityHandler() {
		return electricity;
	}
	
	
}
