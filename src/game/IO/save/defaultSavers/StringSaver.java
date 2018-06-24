/**
 * 
 */
package game.IO.save.defaultSavers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import game.Game;
import game.IO.save.Saver;

/**
 * @author Julius Häger
 *
 */
public class StringSaver implements Saver<String> {
	
	//JAVADOC: StringSaver

	@Override
	public boolean save(Object object, Path location) {
		try {
			Files.write(location, Collections.singleton((String)object));
			return true;
		} catch (IOException e) {
			Game.log.logError("Could not save request: " + location, "IO", "StringSaver", "Save");
			return false;
		}		
	}

	@Override
	public Class<String> getSupportedDataType() {
		return String.class;
	}
}
