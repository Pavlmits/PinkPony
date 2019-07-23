package weightcalculator;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;

/**
 * Creates the weights table
 *
 * @param <T> is the type of object that we will extract the edges
 * @param <V> is the type vertices
 */
public interface WeightCalculator<V, T> {

    Table<V, V, Integer> calculate(final Set<V> vertices, final List<T> possibleEdges);
}
