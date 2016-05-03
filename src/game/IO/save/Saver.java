package game.IO.save;

/**
 * @author Julius Häger
 * @version 1.0
 * @param <T>
 */
public interface Saver<T> {
	
	//JAVADOC: Saver<T>

	/**
	 * @param request
	 * @return
	 */
	public boolean save(SaveRequest<?> request);

	/**
	 * @return
	 */
	public Class<T> getSupportedDataType();

}
