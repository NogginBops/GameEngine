package game.IO.load.defaultLoaders;

import java.nio.file.Path;

import game.IO.load.Loader;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class SoundLoader implements Loader<Sound> {

	// JAVADOC: SoundLoader

	@Override
	public Sound load(Path path) {
		return TinySound.loadSound(path.toFile());
	}

	@Override
	public Class<Sound> getSupportedDataType() {
		return Sound.class;
	}
}
