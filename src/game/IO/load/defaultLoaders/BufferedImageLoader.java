package game.IO.load.defaultLoaders;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.IO.load.LoadRequest;
import game.IO.load.Loader;

/**
 * @author Julius Häger
 * @version 1.0
 */
public class BufferedImageLoader implements Loader<BufferedImage> {
	
	//JAVADOC: BufferedImageLoader
	
	//TODO: Maybe some kind of system for loading a specific image type

	@Override
	public BufferedImage load(LoadRequest<?> request) {
		try {
			
			return ImageIO.read(request.file);
			
		} catch (IOException e) {
			System.err.println(request.file + " could not load!!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Class<BufferedImage> getSupportedDataType() {
		return BufferedImage.class;
	}
}
