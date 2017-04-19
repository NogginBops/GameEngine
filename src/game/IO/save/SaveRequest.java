package game.IO.save;

import java.io.File;

/**
 * @author Julius Häger
 * @version 1.0
 * @param <T>
 */
public class SaveRequest<T> {

	// JAVADOC: SaveRequest
	
	// TODO: File mode enum?
	// Describes what to do when the requested save location does not exist

	/**
	 * 
	 */
	public final T object;

	/**
	 * 
	 */
	public final Class<T> dataType;

	/**
	 * 
	 */
	public final File location;

	/**
	 * 
	 */
	public final String preferredSaver;

	/**
	 * @param object
	 * @param dataType
	 * @param location
	 */
	public SaveRequest(T object, Class<T> dataType, File location) {
		this.object = object;
		this.location = location;
		this.dataType = dataType;
		this.preferredSaver = null;
	}

	/**
	 * @param object
	 * @param dataType
	 * @param location
	 * @param preferredSaver
	 */
	public SaveRequest(T object, Class<T> dataType, File location, String preferredSaver) {
		this.object = object;
		this.location = location;
		this.dataType = dataType;
		this.preferredSaver = preferredSaver;
	}
}
