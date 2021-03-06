package demos.town.buildings.houses;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import demos.town.buildings.Building;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;

/**
 * @author Julius H�ger
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
		built = IOHandler.load(new LoadRequest<BufferedImage>("Built", Paths.get("res/town/House_Built.png"),
				BufferedImage.class, "Default Image Loader")).result;
		building = IOHandler.load(new LoadRequest<BufferedImage>("Building", Paths.get("res/town/House_Building.png"),
				BufferedImage.class, "Default Image Loader")).result;
		outline = IOHandler.load(new LoadRequest<BufferedImage>("Outline", Paths.get("res/town/House_outline.png"),
				BufferedImage.class, "Default Image Loader")).result;
	}
	
	BuildingMode last = null;
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
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
