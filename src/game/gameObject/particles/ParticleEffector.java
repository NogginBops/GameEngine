package game.gameObject.particles;

import java.awt.Color;
import java.util.function.BiPredicate;

import game.util.function.FloatFunction;
import game.util.function.FloatUnaryOperator;
import game.util.math.MathUtils;

/**
 * @author Julius Häger
 *
 */
public abstract class ParticleEffector {
	
	//NOTE: Should the static methods be extracted to a separate class?
	
	/**
	 * 
	 */
	public static final BiPredicate<Particle, Float> ACCEPT_ALL = (particle, deltaTime) -> { return true; };
	
	//NOTE: MICROPERF: The predicate could be built in to the effector, could save a few cycles.
	
	/**
	 * @param pred 
	 * @param func
	 * @return
	 */
	public static ParticleEffector createScaleOverLifetimeEffector(BiPredicate<Particle, Float> pred, FloatUnaryOperator func){
		return new ParticleEffector() {
			@Override
			public void effect(Particle particle, float deltaTime) {
				if(pred.test(particle, deltaTime)){
					//NOTE: Should these be clamped? Or should weird values be accepted
					particle.scaleX = func.applyAsFloat(MathUtils.clamp01(particle.currLifetime / particle.lifetime));
					particle.scaleY = func.applyAsFloat(MathUtils.clamp01(particle.currLifetime / particle.lifetime));
				}
			}
		};
	}
	
	/**
	 * @param pred 
	 * @param func
	 * @return
	 */
	public static ParticleEffector createColorOverLifetimeEffector(BiPredicate<Particle, Float> pred, FloatFunction<Color> func){
		return new ParticleEffector() {
			@Override
			public void effect(Particle particle, float deltaTime) {
				if(pred.test(particle, deltaTime)){
					//NOTE: Should these be clamped? Or should weird values be accepted
					particle.color = func.apply(MathUtils.clamp01(particle.currLifetime / particle.lifetime));
				}
			}
		};
	}
	
	//NOTE: Should this be public?
	
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