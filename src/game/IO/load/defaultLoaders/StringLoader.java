package game.IO.load.defaultLoaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author Julius Häger
 * @version 1.0
 *
 */
public class StringLoader implements Loader<String> {

	// JAVADOC: StringLoader

	@Override
	public String load(LoadRequest<?> request) {
		try {
			FileReader reader = new FileReader(request.file);
			int i = 0;
			String string = "";
			while ((i = reader.read()) != -1) {
				string += (char) i;
			}
			reader.close();
			return string;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<String> getSupportedDataType() {
		return String.class;
	}
}
