package game.IO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import game.Game;
import game.IO.load.LoadRequest;
import game.IO.load.LoadResult;
import game.IO.load.Loader;
import game.IO.load.defaultLoaders.BufferedImageLoader;
import game.IO.load.defaultLoaders.ByteArrayLoader;
import game.IO.load.defaultLoaders.FontLoader;
import game.IO.load.defaultLoaders.MusicLoader;
import game.IO.load.defaultLoaders.SoundLoader;
import game.IO.load.defaultLoaders.StringLoader;
import game.IO.save.SaveRequest;
import game.IO.save.Saver;
import game.IO.save.defaultSavers.ByteArraySaver;
import game.IO.save.defaultSavers.ImageSaver;
import game.IO.save.defaultSavers.StringSaver;
import game.util.ID;
import game.util.IDHandler;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public final class IOHandler {

	// JAVADOC: IOHandeler

	/*
	 * TODO: Virtual folders or some other system to make file management
	 * easier. Something like mount( 'sfx', './res/sfx/' ) and then load(
	 * 'sfx/explosion' ) would actually mean load( './res/sfx/explosion' ). This
	 * would mean implementing some kind of search algorithm thing to figure out
	 * what file the user wants to load.
	 * 
	 * What happens if there are multiple files with the same name? Multiple
	 * mappings to different folders?
	 * 
	 * How would the load request need to change? Would it still use
	 * java.io.File?
	 */

	// TODO: Should IO throw exceptions? It's kind of a inconvenience to require
	// a try catch

	private static IDHandler<Loader<?>> loaderIDHandler;

	private static IDHandler<Saver<?>> saverIDHandler;

	private static HashMap<String, Path> directoryMappings = new HashMap<>();

	// TODO: Does this work?
	// Using LoadRequest<?> as key for type safety
	private static HashMap<LoadRequest<?>, LoadResult<?>> loadCache;

	// Directory shortcuts? and nested directory shortcuts.

	// ".//graphics//entities//enemy//" could just be "enemy/"
	// These will have to be registered somewhere
	// And shortcut paths can be added to existing shortcuts.

	/*
	 * addShortPath(".//graphics//entities//enemy//", "enemy");
	 * 
	 * addShortpath(".//graphics//entities//bosses//", "bosses");
	 * 
	 * extendShortPath("enemy", ".//graphics//entities//bosses//", "bosses");
	 * 
	 * getFile("enemy/bosses/boss_1.png");
	 * 
	 * This would load ".//graphics//entities//bosses//boss_1.png"
	 */

	// Do we benefit anything from nested shortpaths?
	// I can't think of any use at the top of my head.

	static {
		loaderIDHandler = new IDHandler<Loader<?>>();
		saverIDHandler = new IDHandler<Saver<?>>();
		loadCache = new HashMap<>();
		setUpDefaultLoaders();
		setUpDefaultSavers();
	}

	private static void setUpDefaultLoaders() {

		// TODO: More and useful default loaders

		loaderIDHandler.addID(new ID<Loader<?>>("Default String Loader", 0, new StringLoader()));
		loaderIDHandler.addID(new ID<Loader<?>>("Default Image Loader", 1, new BufferedImageLoader()));
		loaderIDHandler.addID(new ID<Loader<?>>("Default Sound Loader", 2, new SoundLoader()));
		loaderIDHandler.addID(new ID<Loader<?>>("Default Music Loader", 3, new MusicLoader()));
		loaderIDHandler.addID(new ID<Loader<?>>("Default Font Loader", 4, new FontLoader()));
		loaderIDHandler.addID(new ID<Loader<?>>("Default Byte Loader", 5, new ByteArrayLoader()));

	}

	private static void setUpDefaultSavers() {

		// TODO: More and useful default savers

		saverIDHandler.addID(new ID<Saver<?>>("Default String Saver", 0, new StringSaver()));
		saverIDHandler.addID(new ID<Saver<?>>("Default PNG Saver", 1, new ImageSaver(ImageSaver.Mode.PNG)));
		saverIDHandler.addID(new ID<Saver<?>>("Default BMP Saver", 2, new ImageSaver(ImageSaver.Mode.BMP)));
		saverIDHandler.addID(new ID<Saver<?>>("Default JPG Saver", 3, new ImageSaver(ImageSaver.Mode.JPG)));
		saverIDHandler.addID(new ID<Saver<?>>("Default GIF Saver", 4, new ImageSaver(ImageSaver.Mode.GIF)));
		saverIDHandler.addID(new ID<Saver<?>>("Default Byte Saver", 5, new ByteArraySaver()));

	}

	/**
	 * @param saver
	 * @param name
	 */
	public static void addSaver(Saver<?> saver, String name) {
		saverIDHandler.addObject(saver, name);
	}

	/**
	 * @param loader
	 * @param name
	 */
	public static void addLoader(Loader<?> loader, String name) {
		loaderIDHandler.addObject(loader, name);
	}

	public static <T> void cache(LoadRequest<T> request){
		if (request.cache == true) {			
			load(request);
		}else {
			Game.log.logWarning("Trying to cache a LoadRequest with the chache flag set to false!", "IO", "Cache");
		}
	}
	
	/**
	 * @param <T>
	 * @param requests
	 * @param queue
	 * @return
	 */
	public static <T> ArrayList<LoadResult<T>> load(ArrayList<LoadRequest<T>> requests) {
		ArrayList<LoadResult<T>> results = new ArrayList<>(requests.size());
		for (LoadRequest<T> req : requests) {
			results.add(load(req));
		}
		return results;
	}

	/**
	 * @param request
	 * @param <T>
	 * @return
	 * 
	 */
	public static <T> LoadResult<T> load(LoadRequest<T> request) {
		if (request.ID != null || request.cache != false) { // Not cached
			if (loadCache.containsKey(request)) {
				T result = request.dataType.cast(loadCache.get(request).result);

				Game.log.logDebug("Found cahced result! ID: " + request.ID, "IO", "Load");

				return new LoadResult<T>(request.ID, result);
			}
		}
		Path fullPath = getFullPath(request.path);
		if (request.preferredLoader != null) {
			Loader<?> preferedLoader = loaderIDHandler.getObject(request.preferredLoader);
			if (preferedLoader != null) {
				if (request.dataType == preferedLoader.getSupportedDataType()) {
					Object res = preferedLoader.load(fullPath);
					LoadResult<T> result = new LoadResult<T>(request.ID, request.dataType.cast(res));
					if (result.result == null) {
						Game.log.logError("Couldn't load request: " + request.ID, "IO", "Load");
					} else if (request.cache) {
						loadCache.put(request, result);
					}
					return result;
				}
			}
			Game.log.logWarning("Could not find prefered loader: '" + request.preferredLoader + "'!");
		}
		for (Loader<?> loader : loaderIDHandler.getAllObjects()) {
			if (request.dataType == loader.getSupportedDataType()) {
				Object res = loader.load(fullPath);
				LoadResult<T> result = new LoadResult<T>(request.ID, request.dataType.cast(res));
				if (result.result == null) {
					Game.log.logError("Couldn't load request: " + request.ID, "IO", "Load");
				} else {
					if (request.cache) {
						loadCache.put(request, result);
					}
					return result;
				}
			}
		}
		Game.log.logError("Couldn't load request: " + request.ID, "IO", "Load");
		return new LoadResult<T>(request.ID, null);
	}

	/**
	 * @param saveRequestQueue
	 * @return
	 */
	public static boolean save(ArrayList<SaveRequest<?>> saveRequestQueue) {
		boolean success = true;
		for (SaveRequest<?> request : saveRequestQueue) {
			if (save(request) == false) {
				success = false;
			}
		}
		return success;
	}

	/**
	 * @param request
	 * @return
	 */
	public static <T> boolean save(SaveRequest<T> request) {
		Path fullPath = getFullPath(request.location);
		if (request.preferredSaver != null) {
			Saver<?> preferedSaver = saverIDHandler.getObject(request.preferredSaver);
			if (preferedSaver != null) {
				if (request.dataType == preferedSaver.getSupportedDataType()) {
					return preferedSaver.save(request.object, fullPath)	;
				}
			}
		}
		for (Saver<?> saver : saverIDHandler.getAllObjects()) {
			if (request.dataType == saver.getSupportedDataType()) {
				return saver.save(request.object, fullPath);
			}
		}
		return false;
	}

	// TODO: Maybe a clear entry? Or should this be handled by the cached tag in
	// LoadRequest

	/**
	 * 
	 */
	public static void purgeLoadCache() {
		loadCache.clear();
	}

	/**
	 * 
	 * @param name
	 * @param directory
	 */
	public static void addDirectoryMapping(String name, Path directory) {
		if (Files.isDirectory(directory) == true) {
			directoryMappings.merge(name, directory, (prev, nev) -> {
				if (prev != null){
					Game.log.logDebug("Remapping " + name + " from \"" + prev + "\" to \"" + nev + "\"", "IO", "DirectoryMap");
				}
				return nev;
			});
			directoryMappings.put(name, directory);
		} else {
			Game.log.logError("Cannot map non-directory path: \"" + directory + "\"");
		}
	}
	
	private static Path getFullPath(Path path){
		if(Files.exists(path)){
			return path;
		}else{
			if (directoryMappings.containsKey(path.getName(0).toString())) {
				Path map = directoryMappings.get(path.getName(0).toString());
				Path file = map.resolve(path.subpath(1, path.getNameCount()));
				if (Files.exists(file)) {
					return file;
				}
			}
			Game.log.logError("Cannot find file \"" + path + "\"!", "IO", "Path");
			return null;
		}
	}
}
