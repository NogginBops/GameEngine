package game.UI;

import java.util.Comparator;

import game.UI.elements.UIElement;

public class UISorter implements Comparator<UIElement> {
	
	public static UISorter instance;
	
	public UISorter() {
		instance = this;
	}

	@Override
	public int compare(UIElement elem1, UIElement elem2) {
		return elem1.getZOrder() - elem2.getZOrder();
	}
}
