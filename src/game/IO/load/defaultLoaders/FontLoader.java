package game.IO.load.defaultLoaders;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author Julius H�ger
 *
 */
public class FontLoader implements Loader<Font> {
	
	//JAVADOC: FontLoader

	@Override
	public Font load(LoadRequest<?> request) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, request.file);
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