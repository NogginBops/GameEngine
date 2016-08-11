package demos.tests;

import java.awt.Color;
import java.awt.Dimension;
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
import game.gameObject.particles.ParticleEffector;
import game.gameObject.particles.ParticleEmitter;
import game.gameObject.particles.ParticleSystem;
import game.gameObject.transform.BoxTransform;
import game.util.math.ColorUtils;
import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public class ParticleTest implements GameInitializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Particle Test");
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", true);
		
		settings.putSetting("Resolution", new Dimension(800, 600));
		
		settings.putSetting("GameInit", new ParticleTest());
		
		new Game(settings).run();
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		Random rand = new Random();
		
		BufferedImage particleImage = null;
		
		try {
			//TODO: The standard 10px particle needs to be more smooth
			particleImage = IOHandler.load(new LoadRequest<BufferedImage>("StandardParticle", new File("./res/particles/StandardParticle_10.png"), BufferedImage.class)).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		int width = 60;
		int height = 60;
		int maxParticles = 10;
		
		int numX = 10;
		int numY = 10;
		
		for (int x = 0; x < numX; x++) {
			for (int y = 0; y < numY; y++) {
				
				ParticleSystem system = new ParticleSystem(new Rectangle2D.Float((int)(x * (width * 1.1f)), (int)(y * (height * 1.1f)), width, height), 5, maxParticles, null);
				
				system.setAllGranularities(10);
				
				ParticleCustomizer customizer = (particle) -> {
					particle.dx = (rand.nextFloat()-0.5f) * 10;
					particle.dy = (rand.nextFloat()-0.5f) * 20;
				};
				
				ParticleEmitter emitter = new ParticleEmitter(0, 0, system.getWidth(), system.getHeight(), maxParticles);
				
				emitter.customizer = customizer;
				
				system.addEmitter(emitter);
				
				ParticleEffector effector = new ParticleEffector() {
					
					@Override
					public void effect(Particle particle, float deltaTime) {
						
						//particle.dx = (rand.nextFloat()-0.5f) * 10;
						//particle.dy = (rand.nextFloat()-0.5f) * 20;
					}
				};
				
				system.addEffector(effector);
				
				system.addEffector(ParticleEffector.createScaleOverLifetimeEffector(ParticleEffector.ACCEPT_ALL, (ratio) -> { return MathUtils.max(0.2f, ratio); })); 
				
				system.addEffector(ParticleEffector.createColorOverLifetimeEffector(ParticleEffector.ACCEPT_ALL, (ratio) -> { return ColorUtils.Lerp(Color.YELLOW, Color.MAGENTA, 1-ratio); }));
				
				system.addImage(0, particleImage);
				
				system.edgeAction = (particle) -> { 
					//This is here to remove the default action of 
					//deactivating the particle when hitting the edge of the system.
				};
				
				system.setDX(rand.nextFloat() * 10);
				
				system.setDY(rand.nextFloat() * 10);
				
				//system.debug = true;
				
				Game.gameObjectHandler.addGameObject(system);
			}
		}*/
		
		ParticleSystem pSystem = new ParticleSystem(new BoxTransform(100, 100, 400, 400), 5, 1000, null);
		
		pSystem.setDR(100);
		
		pSystem.customizeParticles((particle) -> {
			particle.setPosition(pSystem.getX() + ((float)pSystem.getBounds().getWidth() * rand.nextFloat()), pSystem.getY() + ((float)pSystem.getBounds().getHeight() * rand.nextFloat()));
			
			particle.setSize(50, 50);
			
			particle.setLifetime(rand.nextFloat() * 10);
			
			particle.dx = (rand.nextFloat()- 0.5f) * 20;
			particle.dy = (rand.nextFloat()- 0.5f) * 20;
			
			particle.active = true;
		});
		
		pSystem.addImage(0, particleImage);
		
		ParticleEmitter emitter = new ParticleEmitter(120, 200, 100, 10);
		
		emitter.customizer = (particle) -> {
			particle.lifetime = particle.currLifetime = 1;
		};
		
		pSystem.addEmitter(emitter);
		
		pSystem.addEffector(ParticleEffector.createColorOverLifetimeEffector(ParticleEffector.ACCEPT_ALL,
				(ratio) -> {
					return ColorUtils.Lerp(Color.BLUE, Color.red, ratio);
				}));
		
		pSystem.addEffector(ParticleEffector.createScaleOverLifetimeEffector(ParticleEffector.ACCEPT_ALL,
				(ratio) -> {
					return (float) MathUtils.max(0.1f, ratio);
				}));
		
		pSystem.rGranularity = 64;
		pSystem.gGranularity = 64;
		pSystem.bGranularity = 64;
		
		//pSystem.debug = true;
		
		Game.gameObjectHandler.addGameObject(pSystem, "ParticleTest");
		
		ParticleSystem pSys2 = new ParticleSystem(new BoxTransform(100, 100, 500, 500), 5, 2000, null);
		
		pSys2.addImage(0, particleImage);
		
		pSys2.addEmitter(new ParticleEmitter(0, 0, 500, 500, 100f));
		
		pSys2.setDX(20);
		pSys2.setDY(40);
		
		pSys2.debug = true;
		
		//Game.gameObjectHandler.addGameObject(pSys2, "ParticleTest2");
	}
}
