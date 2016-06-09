package game.gameObject.particles;

import java.awt.Color;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author Julius Häger
 *
 */
public abstract class ParticleEffector{
	
	//NOTE: Should the static methods be extracted to a separate class?
	
	/**
	 * @param pred 
	 * @param func
	 * @return
	 */
	public static ParticleEffector createScaleOverLifetimeEffector(BiPredicate<Particle, Float> pred, Function<Float, Float> func){
		return new ParticleEffector() {
			@Override
			public void effect(Particle particle, float deltaTime) {
				if(pred.test(particle, deltaTime)){
					particle.scaleX = func.apply(particle.currLifetime / particle.lifetime);
					particle.scaleY = func.apply(particle.currLifetime / particle.lifetime);
				}
			}
		};
	}
	
	/**
	 * @param pred 
	 * @param func
	 * @return
	 */
	public static ParticleEffector createColorOverLifetimeEffector(BiPredicate<Particle, Float> pred, Function<Float, Color> func){
		return new ParticleEffector() {
			@Override
			public void effect(Particle particle, float deltaTime) {
				if(pred.test(particle, deltaTime)){
					particle.color = func.apply(particle.currLifetime / particle.lifetime);
				}
			}
		};
	}
	
	/**
	 * 
	 */
	public boolean enabled = true;
	
	/**
	 * @param particle
	 * @param deltaTime 
	 */
	public abstract void effect(Particle particle, float deltaTime);
	
}