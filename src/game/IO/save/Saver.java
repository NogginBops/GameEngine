package game.IO.save;

import java.nio.file.Path;

/**
 * @author Julius Häger
 * @version 1.0
 * @param <T>
 */
public interface Saver<T> {
	
	//JAVADOC: Saver<T>

	// TODO: More consistent error messages from default Savers
	
	/**
	 * 
	 * @param object - Is always of type T
	 * @param location
	 * @return
	 */
	public boolean save(Object object, Path location);

	/**
	 * @return
	 */
	public Class<T> getSupportedDataType();

}
