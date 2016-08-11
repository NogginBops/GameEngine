package game.util.math;

import java.awt.Color;

import game.Game;

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
				MathUtils.clamp(MathUtils.Lerp(color1.getBlue(), color2.getBlue(), amount), 0, 255),
				MathUtils.clamp(MathUtils.Lerp(color1.getAlpha(), color2.getAlpha(), amount), 0, 255)
				);
	}
	
	/**
	 * @param color
	 * @param transparncy
	 * @return
	 */
	public static Color createTransparent(Color color, int transparncy){
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparncy);
	}
	
	/**
	 * @param color
	 * @param transparncy
	 * @return
	 */
	public static Color createTransparent(Color color, float transparncy){
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(transparncy * 255));
	}
	
	/**
	 * 
	 * If hue is outside the range [0, 360] it will be automatically wrapped;
	 * 
	 * This algorithm is from: 
	 * <a>http://www.rapidtables.com/convert/color/hsv-to-rgb.htm</a>
	 * 
	 * @param hue
	 * @param saturation
	 * @param value
	 * @return
	 */
	public static Color fromHSV(float hue, float saturation, float value){
		hue = MathUtils.wrap(hue, 0, 360);
		
		float C = saturation * value;
		
		float X = C * (1 - Math.abs(((hue/60)%2)-1));
		
		float m = value - C;
		
		if(0 <= hue && hue < 60){
			return new Color((C + m), (X + m), (0 + m));
		}else if(60 <= hue && hue < 120){
			return new Color((X + m), (C + m), (0 + m));
		}else if(120 <= hue && hue < 180){
			return new Color((0 + m), (C + m), (X + m));
		}else if(180 <= hue && hue < 240){
			return new Color((0 + m), (X + m), (C + m));
		}else if(240 <= hue && hue < 300){
			return new Color((X + m), (0 + m), (C + m));
		}else if(300 <= hue && hue < 360){
			return new Color((C + m), (0 + m), (X + m));
		}
		Game.log.logError("Hue is not in range [0, 360]!? Value = " + hue);
		return null;
	}
}
