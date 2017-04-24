/**
 * 
 */
package game.IO.load.defaultLoaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import game.IO.load.Loader;

/**
 * @author julius.hager
 *
 */
public class ByteArrayLoader implements Loader<byte[]> {
	
	//JAVADOC: ByteArrayLoader
	
	@Override
	public byte[] load(Path path) {
		byte[] result = null;
		try {
			result = Files.readAllBytes(path);
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
