package game.gameObject.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

import game.image.effects.ColorCutoutFilter;

/**
 * @author Julius Häger
 *
 */
public class UniformSpriteSheet {
	
	//JAVADOC: UniformSpriteSheet
	
	//TODO: Put in a better package
	
	private BufferedImage sheet;
	
	private int width, height, margin = 0, padding = 0;
	
	private int horizontalTiles, verticalTiles;
	
	private ColorCutoutFilter cutoutFilter = null;
	
	/**
	 * @param sheet
	 * @param size
	 */
	public UniformSpriteSheet(BufferedImage sheet, int size) {
		if(sheet == null){
			throw new NullPointerException("Sheet can not be a null pointer");
		}
		
		if(size <= 0){
			throw new IllegalArgumentException("Size can not be less than zero.\n Size: " + size);
		}
		
		if(sheet.getWidth() < size || sheet.getHeight() < size) {
			throw new IllegalArgumentException("Size can not be bigger than the actual width of the sheet.\n Image width:" + sheet.getWidth() + ", Size:" + size);
		}
		
		this.sheet = sheet;
		
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
		
		if(width <= 0 || height <= 0){
			throw new IllegalArgumentException("Size in any dimention can not be less than zero.\n Width: " + width + " Height: " + height);
		}
		
		if(sheet.getWidth() < width || sheet.getHeight() < height) {
			throw new IllegalArgumentException("Size can not be bigger than the actual width of the sheet.\n Image width:" + sheet.getWidth() + ", Width: " + width + " Height: " + height);
		}
		
		this.sheet = sheet;
		
		this.width = width;
		this.height = height;
		
		calculateHorizontalTiles();
		calcualteVerticalTiles();
	}
	
	/**
	 * @param sheet
	 * @param width
	 * @param height
	 * @param transparentColor
	 */
	public UniformSpriteSheet(BufferedImage sheet, int width, int height, Color transparentColor) {
		if(sheet == null){
			throw new NullPointerException("Sheet can not be a null pointer");
		}
		
		if(width <= 0 || height <= 0){
			throw new IllegalArgumentException("Size in any dimention can not be less than zero.\n Width: " + width + " Height: " + height);
		}
		
		if(sheet.getWidth() < width || sheet.getHeight() < height) {
			throw new IllegalArgumentException("Size can not be bigger than the actual width of the sheet.\n Image width:" + sheet.getWidth() + ", Width: " + width + " Height: " + height);
		}
		
		this.sheet = sheet;
		
		this.width = width;
		this.height = height;
		
		calculateHorizontalTiles();
		calcualteVerticalTiles();
		
		cutoutFilter = new ColorCutoutFilter(transparentColor, new Color(0, 0, 0, 0), false);
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
		
		if(cutoutFilter != null){
			return cutoutFilter.filter(sheet.getSubimage(xCoord, yCoord, width, height), null);
		}else{
			return sheet.getSubimage(xCoord, yCoord, width, height);
		}
	}
	
	//NOTE: Might change endXY to be width/height inputs instead
	/**
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public BufferedImage getSprite(int startX, int startY, int endX, int endY){
		
		if(startX < 0 || startY < 0 || endX < 0 || endY < 0){
			throw new IllegalArgumentException("Index out of bounds! Index can't be a negative value." + (startX < 0 ? " startX: " + startX : "") + (startY < 0 ? " startY: " + startY : "") + (endX < 0 ? " endX: " + endX : "") + (endY < 0 ? " endY: " + endY : ""));
		}
		
		if(startX > horizontalTiles || startY > verticalTiles || endX > horizontalTiles || endY > verticalTiles){
			throw new IllegalArgumentException("Index out of bounds! The arguments startX: " + startX + ", startY: " + startY + ", endX: " + endX + " and endY: " + endY + ". Bounds width: " + horizontalTiles + " height: " + verticalTiles);
		}
		
		if(startX > endX || startY > endY){
			throw new IllegalArgumentException("Start value must be less than end value! " + (startX > endX ? " startX: " + startX + " endX: " + endX : "") + (startY > endY ? " startY: " + startY + " endY: " + endY : ""));
		}
		
		//TODO: Remove endX and endY and replace them with width and height
		int imgWidth = 1 + (endX - startX);
		int imgHeight = 1 + (endY - startY);
		
		if(cutoutFilter != null){
			return cutoutFilter.filter(sheet.getSubimage(startX * width, startY * height, imgWidth * width, imgHeight * height), null);
		}else{
			return sheet.getSubimage(startX * width, startY * height, imgWidth * width, imgHeight * height);
		}
	}
}
