package game.IO.load.defaultLoaders;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import game.Game;
import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author Julius H�ger
 * @version 1.0
 *
 */
public class StringLoader implements Loader<String> {

	// JAVADOC: StringLoader

	@Override
	public String load(LoadRequest<?> request) {
		try {
			List<String> lines = Files.readAllLines(request.path);
			StringBuilder sb = new StringBuilder(lines.stream().map(String::length).reduce(0, Integer::sum));
			sb.append(lines.stream().reduce(String::concat).orElse(null));
			return sb.toString();
		} catch (IOException e) {
			Game.log.logError("Could not read \"" + request.path + "\"! " + e.toString());
			return null;
		}
	}

	@Override
	public Class<String> getSupportedDataType() {
		return String.class;
	}
}
