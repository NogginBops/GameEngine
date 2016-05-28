package demos.tests;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.graphics.Camera;
import game.gameObject.particles.Particle;
import game.gameObject.particles.ParticleSystem;

/**
 * @author Julius Häger
 *
 */
public class ParticleTest implements GameInitializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "Particle Test");
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", true);
		
		settings.putSetting("GameInit", new ParticleTest());
		
		new Game(settings).run();
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		Random rand = new Random();
		
		BufferedImage particeImage = null;
		
		try {
			particeImage = IOHandler.load(new LoadRequest<BufferedImage>("StandardParticle", new File("./res/particles/StandardParticle_100.png"), BufferedImage.class)).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ParticleSystem pSystem = new ParticleSystem(new Rectangle(100, 100, 400, 400), 5, 1000);
		
		pSystem.addImage(0, particeImage);
		
		Particle[] particles = pSystem.getParticles();
		
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(pSystem.getY() + (pSystem.getBounds().width * rand.nextFloat()), pSystem.getY() + (pSystem.getBounds().height * rand.nextFloat()), 50, 50, rand.nextFloat() * 10, Color.WHITE, 0);
			
			particles[i].dx = (rand.nextFloat()- 0.5f) * 20;
			particles[i].dy = (rand.nextFloat()- 0.5f) * 20;
		}
		
		//Game.gameObjectHandler.addGameObject(pSystem, "ParticleTest");
		
		ParticleSystem pSys2 = new ParticleSystem(new Rectangle(100, 100, 500, 500), 5, 2000);
		
		pSys2.addImage(0, particeImage);
		
		Particle[] particles2 = pSys2.getParticles();
		
		for (int i = 0; i < particles2.length; i++) {
			particles2[i] = new Particle(pSys2.getX() + (pSys2.getBounds().width * rand.nextFloat()), pSys2.getY() + (pSys2.getBounds().height * rand.nextFloat()), 10, 10, 5 + rand.nextFloat() * 10, Color.WHITE, 0);
			
			particles2[i].dx = (rand.nextFloat()- 0.5f) * 20;
			particles2[i].dy = (rand.nextFloat()- 0.5f) * 20;
			
			particles2[i].active = false;
		}
		
		pSys2.addEmitter(pSys2.new ParticleEmitter(pSys2.getX(), pSys2.getX(), 50, 50, 100f));
		
		//FIXME: Something weird is happening with the bounding box when the particle system is moving!
		//Probably a error with relative coordinates.
		
		pSys2.setDX(20);
		pSys2.setDY(40);
		
		Game.gameObjectHandler.addGameObject(pSys2, "ParticleTest2");
	}
}
