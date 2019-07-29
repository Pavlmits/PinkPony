package clustering;

import java.util.Collection;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.nlpub.watset.graph.MaxMax;

public class MaxMaxClustering<V> implements Clustering<V> {

    private final GraphCreator<V> graphCreator;

    public MaxMaxClustering(final GraphCreator<V> graphCreator) {
        this.graphCreator = graphCreator;
    }


    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final MaxMax<V, DefaultWeightedEdge> maxMaxClustering = new MaxMax<>(graphCreator.createSimpleWightedGraph(fileTable));
        maxMaxClustering.fit();
        return maxMaxClustering.getClusters();
    }
}
