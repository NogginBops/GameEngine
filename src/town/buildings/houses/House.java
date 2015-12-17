package town.buildings.houses;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import town.buildings.Building;

/**
 * @author Julius Häger
 *
 */
public class House extends Building {

	protected BufferedImage outline, building, built;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public House(float x, float y, float width, float height) {
		super(x, y, width, height);
		load();
	}

	private void load() {
		try {
			built = IOHandler.load(new LoadRequest<BufferedImage>("Built", new File("res/town/House_Built.png"),
					BufferedImage.class, "Default Image Loader")).result;
			building = IOHandler.load(new LoadRequest<BufferedImage>("Building",
					new File("res/town/House_Building.png"), BufferedImage.class, "Default Image Loader")).result;
			outline = IOHandler.load(new LoadRequest<BufferedImage>("Outline", new File("res/town/House_outline.png"),
					BufferedImage.class, "Default Image Loader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		switch (mode) {
		case OUTLINE:
			g2d.drawImage(outline, (int) x, (int) y, null);
			break;
		case BUILDING:
			g2d.drawImage(building, (int) x, (int) y, null);
			break;
		case BUILT:
			g2d.drawImage(built, (int) x, (int) y, null);
			break;
		default:
			break;
		}
	}
}
