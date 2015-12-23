package game.gameObject.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Julius Häger
 *
 */
public class UniformSpriteSheet {
	
	private BufferedImage sheet;
	
	private int width, height, margin = 0, padding = 0;
	
	private int horizontalTiles, verticalTiles;
	
	private Color transparentColor = null;
	
	/**
	 * @param sheet
	 * @param size
	 */
	public UniformSpriteSheet(BufferedImage sheet, int size) {
		if(sheet == null){
			throw new NullPointerException("Sheet can not be a null pointer");
		}
		this.sheet = sheet;
		
		if(size <= 0){
			throw new IllegalArgumentException("Size can not be less than zero.\n Size: " + size);
		}
		
		if(sheet.getWidth() < size || sheet.getHeight() < size) {
			throw new IllegalArgumentException("Size can not be bigger than the actual width of the sheet.\n Image width:" + sheet.getWidth() + ", Size:" + size);
		}
		
		width = size;
		height = size;
		
		calculateHorizontalTiles();
		calcualteVerticalTiles();
	}
	
	/**
	 * @param sheet
	 * @param width
	 * @param height
	 */
	public UniformSpriteSheet(BufferedImage sheet, int width, int height) {
		if(sheet == null){
			throw new NullPointerException("Sheet can not be a null pointer");
		}
		this.sheet = sheet;
		
		if(width <= 0 || height <= 0){
			throw new IllegalArgumentException("Size in any dimention can not be less than zero.\n Width: " + width + " Height: " + height);
		}
		
		if(sheet.getWidth() < width || sheet.getHeight() < height) {
			throw new IllegalArgumentException("Size can not be bigger than the actual width of the sheet.\n Image width:" + sheet.getWidth() + ", Width: " + width + " Height: " + height);
		}
		
		this.width = width;
		this.height = height;
		
		calculateHorizontalTiles();
		calcualteVerticalTiles();
	}
	
	/**
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * @return
	 */
	public int getPadding(){
		return padding;
	}
	
	/**
	 * @return
	 */
	public int getMargin(){
		return margin;
	}
	
	/**
	 * @return
	 */
	public int getHorizontalTiles(){
		return horizontalTiles;
	}
	
	private void calculateHorizontalTiles(){
		int tiles = sheet.getWidth();
		tiles -= padding * 2;
		tiles += margin;
		tiles /= (width + margin);
		horizontalTiles = tiles;
	}
	
	/**
	 * @return
	 */
	public int getVerticalTiles(){
		return verticalTiles;
	}
	
	private void calcualteVerticalTiles(){
		int tiles = sheet.getHeight();
		tiles -= padding * 2;
		tiles += margin;
		tiles /= (height + margin);
		verticalTiles = tiles;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public BufferedImage getSprite(int x, int y){
		if(x < 0 || y < 0){
			throw new IllegalArgumentException("Index out of bounds! Index can't be a negative value." + (x < 0 ? " x: " + x : "") + (y < 0 ? " y: " + y : ""));
		}
		if(x > horizontalTiles || y > verticalTiles){
			throw new IllegalArgumentException("Index out of bounds! The arguments x: " + x + " and y: " + y + " are out of bounds, width: " + horizontalTiles + " height: " + verticalTiles);
		}
		int xCoord = padding - margin + ((width + margin) * x);
		int yCoord = padding - margin + ((height + margin) * y);
		System.out.println("xCoord: " + xCoord + " yCoord: " + yCoord + " width: " + width + " height: " + height);
		return sheet.getSubimage(xCoord, yCoord, width, height);
	}
}
