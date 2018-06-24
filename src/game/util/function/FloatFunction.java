package game.util.function;

/**
 * @author Julius H�ger
 *
 * @param <R>
 */
@FunctionalInterface
public interface FloatFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(float value);
}