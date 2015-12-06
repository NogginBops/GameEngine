package game.IO.load;

import java.io.File;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 * @param <T> 
 */
public class LoadRequest<T> {
	
	// JAVADOC: LoadRequest<T>
	
	/**
	 * 
	 */
	public final String ID;
	
	/**
	 * 
	 */
	public final File file;
	
	/**
	 * 
	 */
	public final Class<T> dataType;
	
	/**
	 * 
	 */
	public final String preferredLoader;

	/**
	 * 
	 * 
	 * @param ID
	 * @param file
	 * @param returnClassType
	 */
	public LoadRequest(String ID, File file, Class<T> returnClassType) {
		this.ID = ID;
		this.file = file;
		this.dataType = returnClassType;
		preferredLoader = null;
	}
	
	/**
	 * @param ID
	 * @param file
	 * @param returnClassType
	 * @param preferredLoader
	 */
	public LoadRequest(String ID, File file, Class<T> returnClassType, String preferredLoader) {
		this.ID = ID;
		this.file = file;
		this.dataType = returnClassType;
		this.preferredLoader = preferredLoader;
	}
}