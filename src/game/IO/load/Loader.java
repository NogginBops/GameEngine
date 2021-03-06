package game.IO.load;

import java.nio.file.Path;

/**
 * <p>
 * The loader class is used by the IO system to construct objects from files.
 * The loader is responsible for parsing the file in to a usable object of a
 * specific data type T.
 * </p>
 * 
 * @version 1.0
 * @author Julius H�ger
 * @param <T>
 *            - The output data type of the loader.
 */
public interface Loader<T> {
	
	// TODO: More consistent error messages from default Loaders
	
	/**
	 * When the IO system has found a suitable request for this loader this
	 * method is called with that request.
	 * @param path 
	 * 			The path to load
	 * 
	 * @return The loaded and parsed class.
	 */
	public T load(Path path);

	/**
	 * Used to figure out if this loader should be used or not for a specific
	 * request.
	 * 
	 * @return The output data type of this loader.
	 */
	public Class<T> getSupportedDataType();

}
