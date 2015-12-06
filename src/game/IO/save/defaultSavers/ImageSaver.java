package game.IO.save.defaultSavers;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.IO.save.SaveRequest;
import game.IO.save.Saver;

/**
 * @author Julius Häger
 *
 */
public class ImageSaver implements Saver<BufferedImage> {

	/**
	 * @author Julius Häger
	 *
	 */
	public enum Mode{
		
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
	public boolean save(SaveRequest<?> request) {
		switch (mode) {
		case PNG:
			try {
				ImageIO.write((BufferedImage) request.object, "PNG", request.location);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case BMP:
			try {
				ImageIO.write((BufferedImage) request.object, "BMP", request.location);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case JPG:
			try {
				ImageIO.write((BufferedImage) request.object, "JPG", request.location);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case GIF:
			try {
				ImageIO.write((BufferedImage) request.object, "GIF", request.location);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public Class<BufferedImage> getSupportedDataType() {
		return BufferedImage.class;
	}
}