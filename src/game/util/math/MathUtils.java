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
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float wrap(float value, float min, float max){
		if(min > max){
			throw new IllegalArgumentException("Min cannot be larger than max");
		}
		
		if(min == max){
			return min;
		}
		
		return min + ((value - min)%(max - min));
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
	
	/**
	 * Maps value from the range [min, max] to a new range [newMin, newMax]. <br><br>
	 * <b>NOTE:</b> If min == max then this method will return newMin!
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @param newMin
	 * @param newMax
	 * @return
	 */
	public static int map(int value, int min, int max, int newMin, int newMax){
		if(isOutside(value, min, max) != 0){
			throw new IllegalArgumentException("Value can not be outside min and max!");
		}
		
		if(min == max){
			//A special case to avoid a division by zero exception
			//NOTE: What value should this return?
			return newMin;
		}else{
			return newMin + (((value - min)/(max - min)) * newMax);
		}
	}
	
	/**
	 * Maps value from the range [min, max] to a new range [newMin, newMax]. <br><br>
	 * <b>NOTE:</b> If min == max then this method will return newMin!
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @param newMin
	 * @param newMax
	 * @return
	 */
	public static float map(float value, float min, float max, float newMin, float newMax){
		if(isOutside(value, min, max) != 0){
			throw new IllegalArgumentException("Value can not be outside min and max! Value: " + value);
		}
		
		if(min == max){
			//A special case to avoid a division by zero exception
			//NOTE: What value should this return?
			return newMin;
		}else{
			return newMin + (((value - min)/(max - min)) * newMax);
		}
	}
}
