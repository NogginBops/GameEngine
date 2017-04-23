package game.IO.save.defaultSavers;

import java.io.IOException;
import java.nio.file.Files;

import game.Game;
import game.IO.save.SaveRequest;
import game.IO.save.Saver;

/**
 * 
 * @author Julius Häger
 *
 */
public class ByteArraySaver implements Saver<byte[]> {

	//JAVADOC: ByteArraySaver
	
	@Override
	public boolean save(SaveRequest<?> request) {
		try {
			Files.write(request.location, (byte[])request.object);
			return true;
		} catch (IOException e) {
			Game.log.logError("Could not save request: " + request, "IO", "Save", "byte[]");
			return false;
		}
	}

	@Override
	public Class<byte[]> getSupportedDataType() {
		return byte[].class;
	}
}
