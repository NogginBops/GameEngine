package game.UI;

import java.util.Comparator;

import game.UI.elements.UIElement;

/**
 * @author Julius Häger
 *
 */
public class UISorter implements Comparator<UIElement> {
	
	//JAVADOC: UISorter
	
	/**
	 * 
	 */
	public static UISorter instance;
	
	static{
		instance = new UISorter();
	}

	@Override
	public int compare(UIElement elem1, UIElement elem2) {
		return elem1.getZOrder() - elem2.getZOrder();
	}
}
