package clustering;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import dagger.Component;

@Component(modules = ClusteringModule.class)
public interface Clustering<V, T> {

    Collection<Collection<V>> cluster(final Set<V> vertices, final List<T> possibleEdges);
}
