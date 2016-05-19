package game.image.effects;

import java.awt.Color;
import java.awt.image.BufferedImage;

import game.util.image.ImageUtils;

/**
 * @author Julius Häger
 *
 */
public class ColorCutoutFilter extends AbstractFilter{

	private final Color cutoutColor;
	
	private final Color fillColor;
	
	private final boolean compareTransparency;
	
	/**
	 * @param cutoutColor 
	 * @param fillColor 
	 * @param compareTransparency 
	 * 
	 */
	public ColorCutoutFilter(Color cutoutColor, Color fillColor, boolean compareTransparency) {
		this.cutoutColor = cutoutColor;
		this.fillColor = fillColor;
		this.compareTransparency = compareTransparency;
	}
	
	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}

		int width = src.getWidth();
		int height = src.getHeight();

		int[] pixels = new int[width * height];
		ImageUtils.getPixels(src, 0, 0, width, height, pixels);
		replacePixles(pixels);
		ImageUtils.setPixels(dest, 0, 0, width, height, pixels);
		return dest;
	}
	
	private void replacePixles(int[] pixels){
		int cut_a = cutoutColor.getAlpha();
		int cut_r = cutoutColor.getRed();
		int cut_b = cutoutColor.getBlue();
		int cut_g = cutoutColor.getGreen();
		
		for (int i = 0; i < pixels.length; i++) {
			if((!compareTransparency || (((pixels[i] >> 24) & 0xFF) == cut_a)) &&
					(((pixels[i] >> 16) & 0xFF) == cut_r &&
					((pixels[i] >> 8) & 0xFF) == cut_g &&
					(pixels[i] & 0xFF) == cut_b)){
				
				pixels[i] = fillColor.getRGB();
				
			}
		}
	}

}
