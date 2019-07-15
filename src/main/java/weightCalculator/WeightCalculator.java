package weightCalculator;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;

public interface WeightCalculator<T, S> {

    Table<S, S, Integer> calculate(final Set<S> vertices, final List<T> possibleEdges);
}
