package game.image.effects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * 
 *
 */
public class ColorTintFilter extends AbstractFilter {
	
	private final Color mixColor;
	private final float mixValue;

	/**
	 * @param mixColor
	 * @param mixValue
	 */
	public ColorTintFilter(Color mixColor, float mixValue) {
		if (mixColor == null) {
			throw new IllegalArgumentException("mixColor cannot be null");
		}

		this.mixColor = mixColor;
		if (mixValue < 0.0f) {
			mixValue = 0.0f;
		} else if (mixValue > 1.0f) {
			mixValue = 1.0f;
		}
		this.mixValue = mixValue;
	}

	/**
	 * @return
	 */
	public float getMixValue() {
		return mixValue;
	}

	/**
	 * @return
	 */
	public Color getMixColor() {
		return mixColor;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		if (dst == null) {
			dst = createCompatibleDestImage(src, null);
		}

		int width = src.getWidth();
		int height = src.getHeight();

		int[] pixels = new int[width * height];
		getPixels(src, 0, 0, width, height, pixels);
		mixColor(pixels);
		setPixels(dst, 0, 0, width, height, pixels);
		return dst;
	}

	private static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
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

	private static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
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

	private void mixColor(int[] inPixels) {
		float mix_a = mixColor.getAlpha() / 255f;
		float mix_r = mixColor.getRed() / 255f;
		float mix_b = mixColor.getBlue() / 255f;
		float mix_g = mixColor.getGreen() / 255f;

		for (int i = 0; i < inPixels.length; i++) {
			int argb = inPixels[i];

			float a = ((argb >> 24) & 0xFF) / 255f;
			float r = ((argb >> 16) & 0xFF) / 255f;;
			float g = ((argb >> 8) & 0xFF) / 255f;;
			float b = ((argb) & 0xFF) / 255f;;
			r = r * mix_r;
			g = g * mix_g;
			b = b * mix_b;

			inPixels[i] = ((int)(a * 255)) << 24 | ((int)(r * 255)) << 16 | ((int)(g * 255)) << 8 | ((int)(b * 255));
		}
	}
}