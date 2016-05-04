package game.math;

/**
 * @author Julius Häger
 *
 */
public final class MathUtils {
	
	//NumberUtils?
	
	//JAVADOC: MathUtils
	
	/**
	 * @param value1
	 * @param value2
	 * @param amount
	 * @return
	 */
	public static int Lerp(int value1, int value2, float amount){
		return (int)(value1 + (value2 - value1) * amount + 0.5);
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @param amount
	 * @return
	 */
	public static float Lerpf(float value1, float value2, float amount){
		return value1 + (value2 - value1) * amount;
	}
}
