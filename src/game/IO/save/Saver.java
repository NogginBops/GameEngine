package game.IO.save;

/**
 * @author Julius H�ger
 * @version 1.0
 * @param <T>
 */
public interface Saver<T> {

	// TODO: General Output

	// TODO: Save: interface

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
