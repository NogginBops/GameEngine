package game.IO.load.defaultLoaders;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class MusicLoader implements Loader<Music> {

	// JAVADOC: MusicLoader

	@Override
	public Music load(LoadRequest<?> request) {
		return TinySound.loadMusic(request.file);
	}

	@Override
	public Class<Music> getSupportedDataType() {
		return Music.class;
	}

}
