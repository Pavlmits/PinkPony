package clustering;

import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 *
 * @param <V> is the type of vertices
 * @param <T> is the type of object that we will extract the edges
 */
public class KruskalsClustering<V, T> implements Clustering<V> {

    private final GraphCreator<V> graphCreator;

    // desired number of clusters.
    private final int k;

    public KruskalsClustering(final GraphCreator<V> graphCreator, final int k) {
        this.graphCreator = graphCreator;
        this.k = k;
    }

    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable ) {
        final Graph<V, DefaultWeightedEdge> simpleWightedGraph = graphCreator.createSimpleWightedGraph(fileTable);
        PriorityQueue<T> edges = new PriorityQueue<>();



        return Collections.emptyList();
    }
}
