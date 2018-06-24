package game.util;

/**
 * @author Julius Häger
 *
 * @param <T>
 * @param <U>
 */
public class Pair<T, U>{
	
	//JAVADOC: Pair
	
	/**
	 * 
	 */
	public final T key;
	/**
	 * 
	 */
	public final U value;
	
	/**
	 * @param key 
	 * @param value 
	 */
	public Pair(T key, U value) {
		this.key = key;
		this.value = value;
	}
}
