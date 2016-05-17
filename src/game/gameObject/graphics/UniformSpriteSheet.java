package game.gameObject.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
	 * @param sheet
	 * @param width
	 * @param height
	 * @param transparentColor
	 */
	public UniformSpriteSheet(BufferedImage sheet, int width, int height, Color transparentColor) {
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
		
		this.transparentColor = transparentColor;
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
	
	//TODO: Move these functions to some other util method
	
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
		return copyImageAndRemoveColor(sheet.getSubimage(xCoord, yCoord, width, height), transparentColor);
	}
	
	//NOTE: Might change endXY to width/height
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
		int imgWidth = endX - startX;
		int imgHeight = endY - startY;
		BufferedImage image = new BufferedImage(imgWidth * width, imgHeight * height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		for(int x = startX; x <= endX; x++){
			for(int y = startY; y <= endY; y++){
				g2d.drawImage(getSprite(x, y), (x - startX) * width, (y - startY) * height, null);
			}
		}
		return image;
	}
	
	//TODO: Should this be done as a filter? Either way it should be done more efficiently (Raster manipulation?)
	
	private BufferedImage copyImageAndRemoveColor(BufferedImage image, Color color){
		BufferedImage copyImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if(color != null){
			int rgb;
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					rgb = image.getRGB(x, y);
					if(rgb == color.getRGB()){
						copyImage.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
					}else{
						copyImage.setRGB(x, y, image.getRGB(x, y));
					}
				}
			}
		}else{
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					copyImage.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}
		return copyImage;
	}
}
