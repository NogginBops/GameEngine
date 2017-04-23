package game.IO.load.defaultLoaders;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;
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
			return ImageIO.read(request.path.toFile());
		} catch (IOException e) {
			Game.log.logError("Could not load \"" + request.path + "\"", "IO", "Loader", "BufferedImage", "BufferedImageLoader");
			return null;
		}
	}

	@Override
	public Class<BufferedImage> getSupportedDataType() {
		return BufferedImage.class;
	}
}
