package demos.town.buildings;

import game.gameObject.graphics.Sprite;
import game.util.UpdateListener;

/**
 * @author Julius Häger
 *
 */
public abstract class Building extends Sprite implements UpdateListener {
	
	//TODO: Remove

	protected BuildingMode mode;

	protected float centerMarginX, centerMarginY;

	private float timer = 0;
	private float buildTime = 2;

	protected enum BuildingMode {

		OUTLINE, BUILDING, BUILT;

	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * 
	 */
	public Building(float x, float y, int width, int height) {
		super(x, y, width, height);
		centerMarginX = width / 2;
		centerMarginY = height / 2;
		mode = BuildingMode.OUTLINE;
	}

	/**
	 * 
	 */
	public void placed() {
		if (mode == BuildingMode.OUTLINE) {
			mode = BuildingMode.BUILDING;
		}
	}

	@Override
	public void update(float deltaTime) {
		switch (mode) {
		case OUTLINE:

			break;
		case BUILDING:
			if (timer < buildTime) {
				timer += deltaTime;
			} else {
				mode = BuildingMode.BUILT;
				timer = 0;
			}
			break;
		case BUILT:

			break;
		default:
			break;
		}
	}

	/**
	 * @return
	 */
	public float getCenterMarginX() {
		return centerMarginX;
	}

	/**
	 * @param centerMarginX
	 */
	public void setCenterMarginX(float centerMarginX) {
		this.centerMarginX = centerMarginX;
	}

	/**
	 * @return
	 */
	public float getCenterMarginY() {
		return centerMarginY;
	}

	/**
	 * @param centerMarginY
	 */
	public void setCenterMarginY(float centerMarginY) {
		this.centerMarginY = centerMarginY;
	}
}
