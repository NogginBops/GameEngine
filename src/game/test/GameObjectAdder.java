package game.test;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

import game.Game;
import game.gameObject.GameObject;
import game.input.mouse.MouseListener;
import game.util.GameObjectHandler;
import town.buildings.Building;
import town.buildings.houses.House;

/**
 * @author Julius Häger
 *
 */
public class GameObjectAdder implements GameObject, MouseListener{
	
	private int ZOrder = Integer.MAX_VALUE - 8;
	
	private GameObjectHandler gameObjectHandler;
	
	private Random rand;
	
	private Rectangle bounds = new Rectangle(10, 10);
	
	private Building currentBuilding;
	
	/**
	 * @param objectHandler
	 */
	public GameObjectAdder(GameObjectHandler objectHandler) {
		gameObjectHandler = objectHandler;
		rand = new Random();
		currentBuilding = new House(0, 0, 24, 30);
		gameObjectHandler.addGameObject(currentBuilding, "House");
		Game.game.addUpdateListener(currentBuilding);
	}
	
	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {
		
	}

	@Override
	public int getZOrder() {
		return ZOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if(ZOrder == object.getZOrder()){
			return 0;
		}else{
			return ZOrder > object.getZOrder() ? 1 : -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//OtherPaintable p = new OtherPaintable(e.getX(), e.getY(), rand.nextInt(200), rand.nextInt(200), 0, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		//gameObjectHandler.addGameObject(p, "Square");
		currentBuilding.placed();
		currentBuilding.updateBounds();
		currentBuilding = new House(e.getX(), e.getY(), 24, 30);
		gameObjectHandler.addGameObject(currentBuilding, "House");
		Game.game.addUpdateListener(currentBuilding);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentBuilding.setX(e.getX());
		currentBuilding.setY(e.getY());
	}

	@Override
	public void mouseWeelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public boolean absorb() {
		return false;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return true;
	}
}
