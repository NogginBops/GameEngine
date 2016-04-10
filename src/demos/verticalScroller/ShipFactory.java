package demos.verticalScroller;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ShipFactory {
	
	private static HashMap<String, Ship> shipMap = new HashMap<>();
	
	public static void createShip(String name, BufferedImage farLeft, BufferedImage left, BufferedImage center, BufferedImage right, BufferedImage farRight, BufferedImage projectile){
		shipMap.put(name, new Ship(0, 0, farLeft, left, center, right, farRight, projectile, 2));
	}
	
	public static Ship getShip(String name){
		return shipMap.get(name);
	}
}
