package game.IO.load.defaultLoaders;

import java.nio.file.Path;

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
	public Music load(Path path) {
		return TinySound.loadMusic(path.toFile());
	}

	@Override
	public Class<Music> getSupportedDataType() {
		return Music.class;
	}

}
