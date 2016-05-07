package game.math;

import java.awt.Color;

/**
 * @author Julius Häger
 *
 */
public final class ColorUtils {
	
	//JAVADOC: ColorUtils
	
	/**
	 * @param color1
	 * @param color2
	 * @param amount
	 * @return
	 */
	public static Color Lerp(Color color1, Color color2, float amount){
		return new Color(
				MathUtils.clamp(MathUtils.Lerp(color1.getRed(), color2.getRed(), amount), 0, 255),
				MathUtils.clamp(MathUtils.Lerp(color1.getGreen(), color2.getGreen(), amount), 0, 255),
				MathUtils.clamp(MathUtils.Lerp(color1.getBlue(), color2.getBlue(), amount), 0, 255)
				);
	}
}
