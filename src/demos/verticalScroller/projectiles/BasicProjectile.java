package demos.verticalScroller.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BasicProjectile extends Projectile {

	private float baseDX, baseDY;
	
<<<<<<< HEAD
	public BasicProjectile(BufferedImage image, float lifetime, float x, float y, float dx, float dy) {
		super(x, y, image, lifetime);
=======
	private float lifetime;
	
	private float timer;
	
	public BasicProjectile(BufferedImage image, float lifetime, float x, float y, float dx, float dy) {
		super(x, y, image);
		this.lifetime = lifetime;
>>>>>>> origin/GameEngine(Nightly)
		baseDX = dx;
		baseDY = dy;
	}
	
	@Override
	public void update(long timeMillis) {
		timer = timeMillis/1000000000f;
		setDX(baseDX);
		setDY(baseDY);
		super.update(timeMillis);
		if(timer >= lifetime){
			//TODO: Fix/implement self deletion!!
		}
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		super.paint(g2d);
	}
}
