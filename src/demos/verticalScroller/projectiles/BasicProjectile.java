package demos.verticalScroller.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BasicProjectile extends Projectile {

	private float baseDX, baseDY;
	
	public BasicProjectile(BufferedImage image, float lifetime, float x, float y, float dx, float dy) {
		super(x, y, image, lifetime);
		baseDX = dx;
		baseDY = dy;
	}
	
	@Override
	public void update(long timeMillis) {
		setDX(baseDX);
		setDY(baseDY);
		super.update(timeMillis);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		super.paint(g2d);
	}
}
