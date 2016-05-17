package game.util.math;

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
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int clamp(int value, int min, int max){
		if(value < min){
			return min;
		}else if(value > max){
			return max;
		}else{
			return value;
		}
	}
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp(float value, float min, float max){
		if(value < min){
			return min;
		}else if(value > max){
			return max;
		}else{
			return value;
		}
	}
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp01(float value){
		if(value < 0){
			return 0;
		}else if(value > 1){
			return 1;
		}else{
			return value;
		}
	}
}
