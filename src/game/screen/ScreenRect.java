package game.screen;

/**
 * @author Julius Häger
 *
 */
public class ScreenRect {
	
	//JAVADOC: ScreenRects
	
	/**
	 * 
	 */
	public static ScreenRect FULL = new ScreenRect(0, 0, 1, 1);
	
	protected float x, y, x2, y2;
	
	/**
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 */
	public ScreenRect(float x, float y, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @param x2
	 */
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/**
	 * @return
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * @param y2
	 */
	public void setY2(float y2) {
		this.y2 = y2;
	}
}
