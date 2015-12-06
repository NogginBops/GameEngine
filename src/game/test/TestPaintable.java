package game.test;

import game.gameObject.graphics.Building;
import game.gameObject.graphics.Paintable;

import java.awt.Graphics2D;

public class TestPaintable extends Building implements Paintable {
	
	public TestPaintable(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.fillRect(x, y, width, height);
	}
}
