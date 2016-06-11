package demos.tests;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
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
import game.gameObject.particles.ParticleEffector;
import game.gameObject.particles.ParticleEmitter;
import game.gameObject.particles.ParticleSystem;
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
		
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "Particle Test");
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", false);
		
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
		
		int width = 100;
		int height = 100;
		int maxParticles = 100;
		
		int numX = 10;
		int numY = 10;
		
		for (int x = 0; x < numX; x++) {
			for (int y = 0; y < numY; y++) {
				
				ParticleSystem system = new ParticleSystem(new Rectangle2D.Float((int)(x * (width * 1.1f)), (int)(y * (height * 1.1f)), width, height), 5, maxParticles);
				
				system.setAllGranularities(10);
				
				ParticleEmitter.ParticleCustomizer customizer = (particle) -> {
					
				};
				
				ParticleEmitter emitter = new ParticleEmitter(0, 0, system.getWidth(), system.getHeight(), maxParticles);
				
				emitter.customizer = customizer;
				
				system.addEmitter(emitter);
				
				ParticleEffector effector = new ParticleEffector() {
					
					@Override
					public void effect(Particle particle, float deltaTime) {
						
						particle.dx = rand.nextFloat() * 10;
						particle.dy = rand.nextFloat() * 20;
						
					}
				};
				
				system.addEffector(effector);
				
				system.addEffector(ParticleEffector.createScaleOverLifetimeEffector(ParticleEffector.ACCEPT_ALL, (ratio) -> { return MathUtils.max(0.2f, ratio); })); 
				
				system.addEffector(ParticleEffector.createColorOverLifetimeEffector(ParticleEffector.ACCEPT_ALL, (ratio) -> { return ColorUtils.Lerp(Color.YELLOW, Color.MAGENTA, 1-ratio); }));
				
				system.addImage(0, particleImage);
				
				system.setDX(rand.nextFloat() * 10);
				
				system.setDY(rand.nextFloat() * 10);
				
				Game.gameObjectHandler.addGameObject(system);
			}
		}
		
		/*ParticleSystem pSystem = new ParticleSystem(new Rectangle(100, 100, 400, 400), 5, 1000);
		
		pSystem.addImage(0, particleImage);
		
		Particle[] particles = pSystem.getParticles();
		
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(pSystem.getY() + (pSystem.getBounds().width * rand.nextFloat()), pSystem.getY() + (pSystem.getBounds().height * rand.nextFloat()), 50, 50, rand.nextFloat() * 10, Color.WHITE, 0);
			
			particles[i].dx = (rand.nextFloat()- 0.5f) * 20;
			particles[i].dy = (rand.nextFloat()- 0.5f) * 20;
		}
		
		pSystem.setParticles(particles);
		
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
		
		ParticleSystem pSys2 = new ParticleSystem(new Rectangle(100, 100, 500, 500), 5, 2000);
		
		pSys2.addImage(0, particleImage);
		
		Particle[] particles2 = pSys2.getParticles();
		
		for (int i = 0; i < particles2.length; i++) {
			particles2[i] = new Particle(pSys2.getX() + (pSys2.getBounds().width * rand.nextFloat()), pSys2.getY() + (pSys2.getBounds().height * rand.nextFloat()), 10, 10, 5 + rand.nextFloat() * 10, Color.WHITE, 0);
			
			particles2[i].dx = (rand.nextFloat()- 0.5f) * 20;
			particles2[i].dy = (rand.nextFloat()- 0.5f) * 20;
			
			particles2[i].color = Color.cyan;
			
			particles2[i].active = false;
		}
		
		pSys2.addEmitter(new ParticleEmitter(0, 0, 500, 500, 100f));
		
		//FIXME: Something weird is happening with the bounding box when the particle system is moving!
		//Probably a error with relative coordinates.
		
		pSys2.setDX(20);
		pSys2.setDY(40);
		
		pSys2.debug = true;
		
		//Game.gameObjectHandler.addGameObject(pSys2, "ParticleTest2");*/
	}
}
