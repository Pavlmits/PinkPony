package clustering;

import java.util.Collection;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.nlpub.watset.graph.MarkovClustering;

/**
 * Graph clustering with Markov
 *
 * @param <V> is the type of vertices
 */
public class MarkovSimpleClustering<V> implements Clustering<V> {

    private final int e;

    private final double r;

    private final GraphCreator<V> graphCreator;

    MarkovSimpleClustering(final int e, final double r, final GraphCreator<V> graphCreator) {
        this.e = e;
        this.r = r;
        this.graphCreator = graphCreator;
    }


    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final MarkovClustering<V, DefaultWeightedEdge> markovClustering = new MarkovClustering<>(graphCreator.createSimpleWightedGraph(fileTable), e, r);
        markovClustering.fit();
        return markovClustering.getClusters();

    }

}
