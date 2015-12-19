/**
 * 
 */
package game.IO.save.defaultSavers;

import java.io.FileWriter;
import java.io.IOException;

import game.IO.save.SaveRequest;
import game.IO.save.Saver;
import game.IO.save.SaverUtil;

/**
 * @author Julius Häger
 *
 */
public class StringSaver implements Saver<String> {

	@Override
	public boolean save(SaveRequest<?> request) {
		if (SaverUtil.makeFileUsable(request.location)) {
			try {
				FileWriter writer = new FileWriter(request.location);
				writer.write((String) request.object);
				writer.flush();
				writer.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public Class<String> getSupportedDataType() {
		return String.class;
	}
}
