package game.IO.save.defaultSavers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import game.Game;
import game.IO.save.Saver;

/**
 * 
 * @author Julius Häger
 *
 */
public class ByteArraySaver implements Saver<byte[]> {

	//JAVADOC: ByteArraySaver
	
	@Override
	public boolean save(Object object, Path location) {
		try {
			Files.write(location, (byte[])object);
			return true;
		} catch (IOException e) {
			Game.log.logError("Could not save request: " + location, "IO", "Save", "byte[]");
			return false;
		}
	}

	@Override
	public Class<byte[]> getSupportedDataType() {
		return byte[].class;
	}
}
