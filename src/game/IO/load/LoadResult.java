package game.IO.load;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 * @param <T>
 */
public class LoadResult<T> {

	// JAVADOC: LoadResult<T>

	/**
	 * 
	 */
	public final String resultID;

	/**
	 * 
	 */
	public final T result;

	/**
	 * @param resultID
	 * @param result
	 */
	public LoadResult(String resultID, T result) {
		this.resultID = resultID;
		this.result = result;
	}

}
