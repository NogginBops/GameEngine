package game.util;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 * @param <T> 
 */
public class ID<T> {
	
	// JAVADOC: ID
	
	/**
	 * 
	 */
	public final String name;
	
	/**
	 * 
	 */
	public final int id;
	
	/**
	 * 
	 */
	public final T object;

	/**
	 * @param name
	 * @param id
	 * @param object 
	 */
	public ID(String name, int id, T object) {
		this.name = name;
		this.id = id;
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "ID[Name/ID: \"" + name + "\n ID: " + id + "]";
	}
}
