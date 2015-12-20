package demos.town.buildings;

import game.gameObject.graphics.Sprite;
import game.util.UpdateListener;

/**
 * @author Julius Häger
 *
 */
public abstract class Building extends Sprite implements UpdateListener {

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
	public Building(float x, float y, float width, float height) {
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
	public void update(long timeNano) {
		switch (mode) {
		case OUTLINE:

			break;
		case BUILDING:
			if (timer < buildTime) {
				timer += timeNano / 1000000000f;
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

	@Override
	public void updateBounds() {
		super.updateBounds();
		centerMarginX = width / 2;
		centerMarginY = height / 2;
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
