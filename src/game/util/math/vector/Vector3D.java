package game.util.math.vector;

/**
 * @author Julius Häger
 *
 */
public class Vector3D extends Vector2D {
	
	//JAVADOC: Vector3D
	
	//TODO: Vector4D?
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static Vector3D add(Vector3D lhs, Vector3D rhs){
		return new Vector3D(lhs.x + rhs.x, lhs.y + rhs.y, lhs.z + rhs.z);
	}
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static Vector3D sub(Vector3D lhs, Vector3D rhs){
		return new Vector3D(lhs.x - rhs.x, lhs.y - rhs.y, lhs.z - rhs.z);
	}
	
	/**
	 * @param vec 
	 * @param scalar
	 * @return 
	 */
	public static Vector3D mult(Vector3D vec, float scalar){
		return new Vector3D(vec.x * scalar, vec.y * scalar, vec.z * scalar);
	}
	
	/**
	 * @param vec 
	 * @param scalar
	 * @return 
	 */
	public static Vector3D div(Vector3D vec, float scalar){
		return new Vector3D(vec.x / scalar, vec.y / scalar, vec.z / scalar);
	}
	
	/**
	 * @param rhs 
	 * @param lhs 
	 * @return 
	 */
	public static float dot(Vector3D lhs, Vector3D rhs){
		return (lhs.x * rhs.x) + (lhs.y * rhs.y) + (lhs.z * rhs.z);
	}
	
	/**
	 * @param lhs 
	 * @param rhs 
	 * @return 
	 */
	public static Vector3D cross(Vector3D lhs, Vector3D rhs){
		return new Vector3D(
				((lhs.y * rhs.z) - (lhs.z * rhs.y)),
				((lhs.z * rhs.x) - (lhs.x * rhs.z)),
				((lhs.x * rhs.y) - (lhs.y * rhs.x))
		);
	}
	
	/**
	 * @param vec
	 * @return
	 */
	public static Vector3D normalize(Vector3D vec){
		float length = vec.getLength();
		return new Vector3D(vec.x / length, vec.y / length, vec.z / length);
	}
	
	/**
	 * 
	 */
	public float z;
	
	/**
	 * 
	 */
	public Vector3D() {
		super();
		z = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3D(float x, float y, float z){
		super(x, y);
		
		this.z = z;
	}
	
	@Override
	public float getLengthSqr() {
		return (x * x) + (y * y) + (z * z);
	}
	
	@Override
	public float getLength() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
}
