package game.util.math.vector;

/**
 * @author Julius Häger
 *
 */
public class Vector2D {
	
	//JAVADOC: Vector2D
	
	public static final Vector2D RIGHT = new Vector2D(1, 0);
	public static final Vector2D LEFT = new Vector2D(-1, 0);
	public static final Vector2D UP = new Vector2D(0, 1);
	public static final Vector2D DOWN = new Vector2D(0, -1);
	public static final Vector2D ZERO = new Vector2D(0, 0);
	public static final Vector2D ONE = new Vector2D(1, 1);
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static Vector2D add(Vector2D lhs, Vector2D rhs){
		return new Vector2D(lhs.x + rhs.x, lhs.y + rhs.y);
	}
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static Vector2D sub(Vector2D lhs, Vector2D rhs){
		return new Vector2D(lhs.x - rhs.x, lhs.y - rhs.y);
	}
	
	/**
	 * @param vec 
	 * @param scalar
	 * @return 
	 */
	public static Vector2D mult(Vector2D vec, float scalar){
		return new Vector2D(vec.x * scalar, vec.y * scalar);
	}
	
	/**
	 * @param vec 
	 * @param scalar
	 * @return 
	 */
	public static Vector2D div(Vector2D vec, float scalar){
		return new Vector2D(vec.x / scalar, vec.y / scalar);
	}
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static float dot(Vector2D lhs, Vector2D rhs){
		return (lhs.x * rhs.x) + (lhs.y * rhs.y);
	}
	
	/**
	 * Returns a perpendicular vector
	 * 
	 * @param vec 
	 * @return 
	 */
	public static Vector2D perp(Vector2D vec){
		return new Vector2D(vec.y, -vec.x);
	}
	
	/**
	 * @param vec
	 * @return
	 */
	public static Vector2D normalize(Vector2D vec){
		float length = vec.getLength();
		return new Vector2D(vec.x / length, vec.y / length);
	}
	
	/**
	 * @param source
	 * @param listener
	 * @return
	 */
	public static float distance(Vector2D from, Vector2D to) {
		return (float)Math.sqrt(distanceSqr(from, to));
	}

	/**
	 * @param from
	 * @param to
	 * @return
	 */
	public static float distanceSqr(Vector2D from, Vector2D to) {
		return (from.x - to.x) * (from.x - to.x)
				+ (from.y - to.y) * (from.y - to.y);
	}
	
	public static float angle(Vector2D from, Vector2D to){
		return (float) Math.toDegrees(Math.atan2((from.x * to.y) - (from.y * to.x), (from.x * to.x) + (from.y * to.y)));
	}
	
	/**
	 * 
	 */
	public float x;
	/**
	 * 
	 */
	public float y;
	
	/**
	 * 
	 */
	public Vector2D() {
		x = 0;
		y = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * @return
	 */
	public float getLengthSqr(){
		return (x * x) + (y * y);
	}
	
	/**
	 * @return
	 */
	public float getLength() {
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	@Override
	public String toString() {
		return super.toString() + " [X=" + x + ",Y=" + y + "]";
	}
}
