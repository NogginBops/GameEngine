package game.gameObject.graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Game;
import game.image.effects.ColorCutoutFilter;

/**
 * @author Julius Häger
 *
 */
public class MappedSpriteSheet {
	
	private Rectangle sheetRect;
	
	private HashMap<String, Rectangle> mappings;
	
	private BufferedImage sheet;

	private ColorCutoutFilter colorFilter;
	
	/**
	 * @param sheet
	 * @param cutout
	 */
	public MappedSpriteSheet(BufferedImage sheet, Color cutout) {
		colorFilter = new ColorCutoutFilter(cutout, new Color(0, 0, 0, 0), false);
		
		this.sheet = colorFilter.filter(sheet, null);
		
		sheetRect = new Rectangle(sheet.getWidth(), sheet.getHeight());
		
		mappings = new HashMap<String, Rectangle>();
	}	
	
	/**
	 * @param name
	 * @param area
	 */
	public void addMapping(String name, Rectangle area){
		if(!sheetRect.contains(area)){
			throw new IllegalArgumentException("The mapping " + area + " is outside the sheet bounds!");
		}
		
		if(mappings.containsKey(name)){
			Game.log.logWarning("A mapping already exists for " + name + ", Overwriting!", "MappedSpriteSheet", "Sprite", "Mapping");
		}
		
		mappings.put(name, area);
	}
	
	/**
	 * @param name
	 */
	public void removeMapping(String name){
		if(!mappings.containsKey(name)){
			Game.log.logWarning("Trying to remove a non-existant mapping: \"" + name + "\"!", "MappedSpriteSheet", "Sprite", "Mapping");
			return;
		}
		
		mappings.remove(name);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public BufferedImage getSprite(String name){
		if(!mappings.containsKey(name)){
			throw new IllegalArgumentException("There is no mapping with the name: " + name);
		}
		
		Rectangle rect = mappings.get(name);
		
		return sheet.getSubimage(rect.x, rect.y, rect.width, rect.height);
	}
}
