package game.IO.save.defaultSavers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import game.IO.save.SaveRequest;
import game.IO.save.Saver;
import game.IO.save.SaverUtil;

/**
 * 
 * @author Julius Häger
 *
 */
public class ByteArraySaver implements Saver<byte[]> {

	//JAVADOC: ByteArraySaver
	
	@Override
	public boolean save(SaveRequest<?> request) {
		if (SaverUtil.makeFileUsable(request.location)) {
			try (FileOutputStream outStream = new FileOutputStream(request.location)) {
				outStream.write((byte[]) request.object);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return false;
	}

	@Override
	public Class<byte[]> getSupportedDataType() {
		return byte[].class;
	}
}
