/**
 * 
 */
package game.IO.load.defaultLoaders;

import java.io.IOException;
import java.nio.file.Files;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author julius.hager
 *
 */
public class ByteArrayLoader implements Loader<byte[]> {
	
	//JAVADOC: ByteArrayLoader
	
	@Override
	public byte[] load(LoadRequest<?> request) {
		byte[] result = null;
		try {
			result = Files.readAllBytes(request.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Class<byte[]> getSupportedDataType() {
		return byte[].class;
	}
}
