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
	 */
	public final boolean cache;

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
		cache = true;
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
		cache = true;
	}
	
	/**
	 * @param ID
	 * @param file
	 * @param returnClassType
	 * @param cache
	 */
	public LoadRequest(String ID, File file, Class<T> returnClassType, boolean cache) {
		this.ID = ID;
		this.file = file;
		this.dataType = returnClassType;
		this.preferredLoader = null;
		this.cache = cache;
	}
	
	/**
	 * @param ID
	 * @param file
	 * @param returnClassType
	 * @param preferredLoader
	 * @param cache
	 */
	public LoadRequest(String ID, File file, Class<T> returnClassType, String preferredLoader, boolean cache) {
		this.ID = ID;
		this.file = file;
		this.dataType = returnClassType;
		this.preferredLoader = preferredLoader;
		this.cache = cache;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + (cache ? 1231 : 1237);
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		LoadRequest<?> other = (LoadRequest<?>) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (cache != other.cache)
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}
}