/**
 * 
 */
package game.IO.save.defaultSavers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

import game.Game;
import game.IO.save.SaveRequest;
import game.IO.save.Saver;

/**
 * @author Julius Häger
 *
 */
public class StringSaver implements Saver<String> {
	
	//JAVADOC: StringSaver

	@Override
	public boolean save(SaveRequest<?> request) {
		try {
			Files.write(request.location, Collections.singleton((String)request.object));
			return true;
		} catch (IOException e) {
			Game.log.logError("Could not save request: " + request, "IO", "StringSaver", "Save");
			return false;
		}		
	}

	@Override
	public Class<String> getSupportedDataType() {
		return String.class;
	}
}
