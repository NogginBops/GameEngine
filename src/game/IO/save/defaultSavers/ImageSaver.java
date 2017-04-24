package game.IO.save.defaultSavers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import game.Game;
import game.IO.save.Saver;

/**
 * @author Julius Häger
 *
 */
public class ImageSaver implements Saver<BufferedImage> {
	
	//JAVADOC: ImageSaver

	/**
	 * @author Julius Häger
	 *
	 */
	public enum Mode {

		/**
		 * 
		 */
		PNG,

		/**
		 * 
		 */
		BMP,

		/**
		 * 
		 */
		JPG,

		/**
		 * 
		 */
		GIF;
	}

	private Mode mode;

	/**
	 * @param mode
	 */
	public ImageSaver(Mode mode) {
		this.mode = mode;
	}

	@Override
	public boolean save(Object object, Path location) {
		switch (mode) {
		case PNG:
			try {
				ImageIO.write((BufferedImage) object, "PNG", location.toFile());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case BMP:
			try {
				ImageIO.write((BufferedImage) object, "BMP", location.toFile());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case JPG:
			try {
				ImageIO.write((BufferedImage) object, "JPG", location.toFile());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case GIF:
			try {
				ImageIO.write((BufferedImage) object, "GIF", location.toFile());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		Game.log.logError("Could not save image: " + location, "IO", "Save", "BufferedImage");
		return false;
	}

	@Override
	public Class<BufferedImage> getSupportedDataType() {
		return BufferedImage.class;
	}
}