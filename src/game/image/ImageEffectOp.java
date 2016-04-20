package game.image;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * @author Julius Häger
 *
 */
public class ImageEffectOp extends ConvolveOp {

	public ImageEffectOp(Kernel kernel) {
		super(kernel);
		
	}
}
