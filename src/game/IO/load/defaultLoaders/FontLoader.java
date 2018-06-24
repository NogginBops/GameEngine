package game.IO.load.defaultLoaders;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.file.Path;

import game.IO.load.Loader;

/**
 * @author Julius Häger
 *
 */
public class FontLoader implements Loader<Font> {
	
	//JAVADOC: FontLoader

	@Override
	public Font load(Path path) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, path.toFile());
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<Font> getSupportedDataType() {
		return Font.class;
	}
}
