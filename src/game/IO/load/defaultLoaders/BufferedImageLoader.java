package game.IO.load.defaultLoaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.ImageInputStream;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class BufferedImageLoader implements Loader<BufferedImage> {
	
	//JAVADOC: BufferedImageLoader

	@Override
	public BufferedImage load(LoadRequest<?> request) {
		try {
			// Create input stream
			ImageInputStream input = ImageIO.createImageInputStream(request.file);

			try {
			    // Get the reader
			    Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

			    if (!readers.hasNext()) {
			        throw new IllegalArgumentException("No reader for: " + request.file); // Or simply return null
			    }

			    ImageReader reader = readers.next();

			    try {
			        // Set input
			        reader.setInput(input);
			        
			        // Configure the param to use the destination type you want
			        ImageReadParam param = reader.getDefaultReadParam();
			        param.setDestinationType(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB));

			        // Finally read the image, using settings from param
			        return reader.read(0, param);
			    }
			    finally {
			        // Dispose reader in finally block to avoid memory leaks
			        reader.dispose();
			    }
			}
			finally {
			    // Close stream in finally block to avoid resource leaks
			    input.close();
			}
			
			
			//return ImageIO.read(request.file);
		} catch (IOException e) {
			System.err.println(request.file + " could not load!!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<BufferedImage> getSupportedDataType() {
		return BufferedImage.class;
	}
}
