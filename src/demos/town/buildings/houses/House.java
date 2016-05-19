package demos.town.buildings.houses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import demos.town.buildings.Building;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;

/**
 * @author Julius Häger
 *
 */
public class House extends Building {
	
	//FIXME: Remove this and move this into Building

	protected BufferedImage outline, building, built;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public House(float x, float y, int width, int height) {
		super(x, y, width, height);
		load();
		
		setSprite(outline);
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
	
	BuildingMode last = null;
	
	@Override
	public void update(long timeNano) {
		super.update(timeNano);
		
		if(last != mode){
			switch (mode) {
			case OUTLINE:
				setSprite(outline);
				break;
			case BUILDING:
				setSprite(building);
				break;
			case BUILT:
				setSprite(built);
				break;
			default:
				break;
			}
			last = mode;
		}
	}
}
