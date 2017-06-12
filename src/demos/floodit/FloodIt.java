package demos.floodit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Stack;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.BasicGameObject;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Paintable;
import game.util.math.vector.Vector2D;

/**
 * @author Julius Häger
 *
 */
public class FloodIt extends BasicGameObject implements Paintable {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Flood It!");
		
		settings.putSetting("Resolution", new Dimension(750, 700));
		
		settings.putSetting("GameInit", new GameInitializer() {
			
			@Override
			public void initialize(GameSettings settings) {
				settings.getSettingAs("MainCamera", Camera.class).setBackgroundColor(new Color(10, 20, 30));
				
				Game.gameObjectHandler.addGameObject(new FloodIt(20, 20, new Vector2D(25, 25)), "FloodIt");
			}
		});
		
		Game.setup(settings);
		
		Game.run();
	}
	
	private int moves = 35;
	
	private Color[] colors = new Color[]{ Color.blue, Color.red, Color.green, Color.yellow, Color.orange, Color.magenta };
	
	private Color[][] board;
	
	private Vector2D tileSize;
	
	/**
	 * @param width 
	 * @param height 
	 * @param tileSize 
	 * 
	 */
	public FloodIt(int width, int height, Vector2D tileSize) {
		super(0, 0, width * tileSize.x, height * tileSize.y, 5);
		
		this.tileSize = tileSize;
		
		transform.setPosition(150, 40);
		
		board = new Color[width][height];
		
		Random rand = new Random();
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = colors[rand.nextInt(colors.length)];
			}
		}
		
		for (int i = 0; i < colors.length; i++) {
			int color = i;
			ColorButton button = new ColorButton(25 + (i * 120), 580, 100, 100, colors[i], () -> { fill(color); });
			
			Game.gameObjectHandler.addGameObject(button, "Button" + i);
		}
	}
	
	private void fill(int color){
		if(board[0][0].equals(colors[color])){
			return;
		}
		
		if(moves <= 0){
			//Already lost!
			return;
		}
		
		moves--;
		
		if(moves <= 0){
			//TODO: Lose
			System.out.println("You lose!");
			return;
		}
		
		boolean[][] mask = new boolean[board.length][board[0].length];
		
		Color target = board[0][0];
		
		Stack<Vector2D> Q = new Stack<>();
		
		Q.add(new Vector2D());

		while (Q.isEmpty() == false) {
			
			Vector2D N = Q.pop();
			
			int e = (int) N.x;
			int w = (int) N.x;
			
			while(e >= 0 && e < board.length && board[e][(int) N.y].equals(target)){
				e++;
			}
			e--;
			
			while (w >= 0 && w < board.length && board[w][(int) N.y].equals(target)) {
				w--;
			}
			w++;
			
			w = w < 0 ? 0 : w;
			e = e >= board.length ? board.length - 1 : e;
			
			for (int n = w; n <= e; n++) {
				
				board[n][(int) N.y] = colors[color];
				
				mask[n][(int) N.y] = true;
				
				if((N.y - 1) >= 0 && (N.y - 1) < board[n].length && board[n][(int)N.y - 1].equals(target)){
					Q.add(new Vector2D(n, N.y - 1));
				}
				
				if((N.y + 1) >= 0 && (N.y + 1) < board[n].length && board[n][(int)N.y + 1].equals(target)){
					Q.add(new Vector2D(n, N.y + 1));
				}
			}
		}
		
		boolean won = true;
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				won &= board[x][y].equals(colors[color]);
			}
		}
		
		//TODO: Win
		if(won){
			System.out.println("You won!");
		}
	}
	
	@Override
	public BufferedImage getImage() {
		return null;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.PINK);
		
		for (int i = 0; i < moves; i++) {
			g2d.fillRect(25, 540 - (i * 15), 50, 10);
		}
		
		g2d.setColor(Color.white);
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				g2d.setColor(board[x][y]);
				
				g2d.fillRect((int)(transform.getX() + (x * tileSize.x)), (int)(transform.getY() + (y * tileSize.y)), (int)tileSize.x, (int)tileSize.y);
			}
		}
	}
}
