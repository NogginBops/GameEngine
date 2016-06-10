package game.util.math;

/**
 * @author Julius H�ger
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
	public static int clamp01(int value){
		if(value < 0){
			return 0;
		}else if(value > 1){
			return 1;
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
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp01(double value){
		if(value < 0){
			return 0;
		}else if(value > 1){
			return 1;
		}else{
			return value;
		}
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static int min(int value1, int value2){
		return value1 > value2 ? value2 : value1;
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static int max(int value1, int value2){
		return value1 < value2 ? value2 : value1;
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static float min(float value1, float value2){
		return value1 > value2 ? value2 : value1;
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static float max(float value1, float value2){
		return value1 < value2 ? value2 : value1;
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double min(double value1, double value2){
		return value1 > value2 ? value2 : value1;
	}
	
	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double max(double value1, double value2){
		return value1 < value2 ? value2 : value1;
	}
	
	/**
	 * Returns a int -1, 0 or 1 if the <code>value</code> is either below min,
	 *  in between min and max or if it is above max respectively.
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int isOutside(int value, int min, int max){
		return value < min ? -1 : value > max ? 1 : 0;
	}
	
	/**
	 * Returns a int -1, 0 or 1 if the <code>value</code> is either below min,
	 *  in between min and max or if it is above max respectively.
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int isOutside(float value, float min, float max){
		return value < min ? -1 : value > max ? 1 : 0;
	}
}
