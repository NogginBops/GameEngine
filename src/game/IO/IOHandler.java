package game.IO;

import java.io.IOException;
import java.util.ArrayList;

import game.IO.load.LoadRequest;
import game.IO.load.LoadResult;
import game.IO.load.Loader;
import game.IO.load.defaultLoaders.BufferedImageLoader;
import game.IO.load.defaultLoaders.MusicLoader;
import game.IO.load.defaultLoaders.SoundLoader;
import game.IO.load.defaultLoaders.StringLoader;
import game.IO.save.SaveRequest;
import game.IO.save.Saver;
import game.IO.save.defaultSavers.ImageSaver;
import game.IO.save.defaultSavers.StringSaver;
import game.util.ID;
import game.util.IDHandler;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class IOHandler {

	// JAVADOC: IOHandeler

	// TODO: Set up static loading methods and non static exit save methods

	private static IDHandler<Loader<?>> loaderIDHandler;

	private static IDHandler<Saver<?>> saverIDHandler;

	static {
		IOHandler.loaderIDHandler = new IDHandler<Loader<?>>();
		IOHandler.saverIDHandler = new IDHandler<Saver<?>>();
		setUpDefaultLoaders();
		setUpDefaultSavers();
	}

	private static void setUpDefaultLoaders() {
		loaderIDHandler.addObject(new ID<Loader<?>>("Default String Loader", 0, new StringLoader()));
		loaderIDHandler.addObject(new ID<Loader<?>>("Default Image Loader", 1, new BufferedImageLoader()));
		loaderIDHandler.addObject(new ID<Loader<?>>("Default Sound Loader", 2, new SoundLoader()));
		loaderIDHandler.addObject(new ID<Loader<?>>("Default Music Loader", 3, new MusicLoader()));
	}

	private static void setUpDefaultSavers() {
		saverIDHandler.addObject(new ID<Saver<?>>("Default String Saver", 0, new StringSaver()));
		saverIDHandler.addObject(new ID<Saver<?>>("Default PNG Saver", 1, new ImageSaver(ImageSaver.Mode.PNG)));
		saverIDHandler.addObject(new ID<Saver<?>>("Default BMP Saver", 2, new ImageSaver(ImageSaver.Mode.BMP)));
		saverIDHandler.addObject(new ID<Saver<?>>("Default JPG Saver", 3, new ImageSaver(ImageSaver.Mode.JPG)));
		saverIDHandler.addObject(new ID<Saver<?>>("Default GIF Saver", 4, new ImageSaver(ImageSaver.Mode.GIF)));
	}

	/**
	 * @param saver
	 * @param name
	 */
	public void addSaver(Saver<?> saver, String name) {
		saverIDHandler.addObject(saver, name);
	}

	/**
	 * @param loader
	 * @param name
	 */
	public void addLoader(Loader<?> loader, String name) {
		loaderIDHandler.addObject(loader, name);
	}

	/**
	 * @param requests
	 * @param queue
	 * @return
	 */
	public static ArrayList<LoadResult<?>> load(ArrayList<LoadRequest<?>> requests) {
		ArrayList<LoadResult<?>> results = new ArrayList<>(requests.size());
		for (LoadRequest<?> req : requests) {
			try {
				results.add(load(req));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	/**
	 * @param request
	 * @param <T>
	 * @return
	 * @throws IOException
	 * 
	 */
	public static <T> LoadResult<T> load(LoadRequest<T> request) throws IOException {
		if (request.preferredLoader != null) {
			Loader<?> preferedLoader = loaderIDHandler.getObject(request.preferredLoader);
			if (preferedLoader != null) {
				if (request.dataType == preferedLoader.getSupportedDataType()) {
					Object res = preferedLoader.load(request);
					LoadResult<T> result = new LoadResult<T>(request.ID, request.dataType.cast(res));
					if (result.result == null) {
						throw new IOException("Couldn't load request: " + request.ID);
					} else {
						return result;
					}
				}
			}
		}
		for (Loader<?> loader : loaderIDHandler.getAllObjects()) {
			if (request.dataType == loader.getSupportedDataType()) {
				Object res = loader.load(request);
				LoadResult<T> result = new LoadResult<T>(request.ID, request.dataType.cast(res));
				if (result.result == null) {
					throw new IOException("Couldn't load request: " + request.ID);
				} else {
					return result;
				}
			}
		}
		throw new IOException("Couldn't load request: " + request.ID);
	}

	/**
	 * @param saveRequestQueue
	 * @return
	 */
	public static boolean save(ArrayList<SaveRequest<?>> saveRequestQueue) {
		boolean success = true;
		for (SaveRequest<?> request : saveRequestQueue) {
			if (request.preferredSaver != null) {
				Saver<?> preferedSaver = saverIDHandler.getObject(request.preferredSaver);
				if (preferedSaver != null) {
					if (request.dataType == preferedSaver.getSupportedDataType()) {
						if (preferedSaver.save(request)) {
							continue;
						}
						success = false;
					}
				}
			}
			for (Saver<?> saver : saverIDHandler.getAllObjects()) {
				if (request.dataType == saver.getSupportedDataType()) {
					if (saver.save(request)) {
						continue;
					}
					success = false;
				}
			}
		}
		return success;
	}
}
