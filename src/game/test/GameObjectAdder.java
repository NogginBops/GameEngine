package game.test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import demos.town.buildings.Building;
import demos.town.buildings.houses.House;
import game.Game;
import game.gameObject.BasicGameObject;
import game.gameObject.GameObject;
import game.input.keys.KeyListener;
import game.input.mouse.MouseListener;

/**
 * @author Julius Häger
 *
 */
public class GameObjectAdder extends BasicGameObject implements MouseListener, KeyListener {
	
	//Remove/Relocate

	private Building currentBuilding;

	/**
	 * @param objectHandler
	 */
	public GameObjectAdder() {
		super(new Rectangle2D.Float(0, 0, 10, 10), Integer.MAX_VALUE - 10);
		currentBuilding = new House(0, 0, 24, 30);
		Game.gameObjectHandler.addGameObject(currentBuilding, "House");
		
		Game.keyHandler.addKeyBinding("Clear", KeyEvent.VK_BACK_SPACE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// OtherPaintable p = new OtherPaintable(e.getX(), e.getY(),
		// rand.nextInt(200), rand.nextInt(200), 0, new Color(rand.nextInt(255),
		// rand.nextInt(255), rand.nextInt(255)));
		// gameObjectHandler.addGameObject(p, "Square");
		currentBuilding.placed();
		currentBuilding.updateBounds();
		currentBuilding = new House(e.getX(), e.getY(), 24, 30);
		Game.gameObjectHandler.addGameObject(currentBuilding, "House");
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
		currentBuilding.placed();
		currentBuilding.updateBounds();
		currentBuilding = new House(e.getX(), e.getY(), 24, 30);
		Game.gameObjectHandler.addGameObject(currentBuilding, "House");
	}

	@Override
	public boolean absorb() {
		return false;
	}

	@Override
	public boolean souldReceiveMouseInput() {
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(Game.keyHandler.isBound("Clear", e.getKeyCode())){
			for (GameObject gameObject : Game.gameObjectHandler.getAllActiveGameObjectsExtending(currentBuilding.getClass())) {
				Game.gameObjectHandler.removeGameObject(gameObject);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public boolean shouldReceiveKeyboardInput() {
		return true;
	}
}
