package game.util.image;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * @author Julius Häger
 *
 */
public final class ImageUtils {

	// TODO: Remove
	/**
	 * 
	 */
	public static int calls = 0;

	// TODO: Remove
	/**
	 * 
	 */
	public static int usefullCalls = 0;

	// TODO: Is this really generating a preferable result? Figure out what to
	// do to make it work as wanted.
	// It's seems like its making a difference, but the images produced are not
	// being accelerated.

	/**
	 * This method is called for every image loaded using the default image
	 * loaders.
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toSystemOptimizedImage(BufferedImage image) {

		calls++;

		if (image == null) {
			return null;
		}

		// Obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		/*
		 * If image is already compatible and optimized for current system
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel())) {

			return image;
		}

		// Image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(),
				image.getTransparency());

		// Get the graphics context of the new image to draw the old image on
		Graphics2D g2d = new_image.createGraphics();

		// Actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		usefullCalls++;

		// Return the new optimized image
		return new_image;
	}

	/**
	 * @param width
	 * @param height
	 * @param transparency
	 * @return
	 */
	public static BufferedImage createSystemOptimizedImage(int width, int height, int transparency) {
		if (width <= 0) {
			throw new IllegalArgumentException("Width cannot be less than or equal to zero!");
		} else if (height <= 0) {
			throw new IllegalArgumentException("Height cannot be less than or equal to zero!");
		} else if ((transparency != BufferedImage.OPAQUE || transparency != BufferedImage.BITMASK
				|| transparency != BufferedImage.TRANSLUCENT) == false) {
			throw new IllegalArgumentException("Transparency must be either OPAQUE, BITMASK or TRANSLUCENT!");
		}
		
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().createCompatibleImage(width, height, transparency);
	}

	/**
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage createSystemOptimizedImage(int width, int height) {
		return createSystemOptimizedImage(width, height, BufferedImage.TRANSLUCENT);
	}

	// TODO: A better system for working with image filters

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param pixels
	 * @return
	 */
	public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
		if (w == 0 || h == 0) {
			return new int[0];
		}
		if (pixels == null) {
			pixels = new int[w * h];
		} else if (pixels.length < w * h) {
			throw new IllegalArgumentException("pixels array must have a length >= w*h");
		}

		int imageType = img.getType();
		if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
			Raster raster = img.getRaster();
			return (int[]) raster.getDataElements(x, y, w, h, pixels);
		}

		return img.getRGB(x, y, w, h, pixels, 0, w);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param pixels
	 */
	public static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
		if (pixels == null || w == 0 || h == 0) {
			return;
		} else if (pixels.length < w * h) {
			throw new IllegalArgumentException("pixels array must have a length >= w*h");
		}

		int imageType = img.getType();
		if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
			WritableRaster raster = img.getRaster();
			raster.setDataElements(x, y, w, h, pixels);
		} else {
			img.setRGB(x, y, w, h, pixels, 0, w);
		}
	}

}
