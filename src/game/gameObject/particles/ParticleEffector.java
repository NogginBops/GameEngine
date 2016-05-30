package game.gameObject.particles;

/**
 * @author Julius Häger
 *
 */
public abstract class ParticleEffector{
	
	/**
	 * @author Julius Häger
	 * @param <T> 
	 *
	 */
	public interface OverLifteimeFunction<T>{
		/**
		 * @param lifetimeRatio
		 * @return
		 */
		public T f(float lifetimeRatio);
	}
	
	/**
	 * @param func
	 * @return
	 */
	public static ParticleEffector getScaleWithLifetimeEffector(OverLifteimeFunction<Float> func){
		return new ParticleEffector() {
			
			@Override
			public void effect(Particle[] particles, float deltaTime) {
				for (int i = 0; i < particles.length; i++) {
					particles[i].scaleX = func.f(particles[i].currLifetime / particles[i].lifetime);
					particles[i].scaleY = func.f(particles[i].currLifetime / particles[i].lifetime);
							//(float) MathUtils.max(0.2f, ((particles[i].currLifetime / particles[i].lifetime)));
				}
			}
		};
	}
	
	/**
	 * 
	 */
	public boolean enabled = true;
	
	/**
	 * @param particles
	 * @param deltaTime 
	 */
	public abstract void effect(Particle[] particles, float deltaTime);
	
}