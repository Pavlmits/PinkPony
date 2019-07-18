package clustering;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.nlpub.watset.graph.MarkovClustering;

/**
 *
 * @param <V> is the type of vertices
 * @param <T> is the type of object that we will extract the edges
 */
public class WatsetClustering<V, T> implements Clustering<V,T> {

    private final int e;

    private final double r;

    private final GraphCreator< T, V> graphCreator;

    public WatsetClustering(final int e, final double r, final GraphCreator<T, V> graphCreator) {
        this.e = e;
        this.r = r;
        this.graphCreator = graphCreator;
    }


    @Override
    public Collection<Collection<V>> cluster(final Set<V> vertices, final List<T> possibleEdgese) {
        final MarkovClustering<V, DefaultWeightedEdge> markovClustering = new MarkovClustering<>(graphCreator.createSimpleWightedGraph(vertices, possibleEdgese), e, r);
        markovClustering.fit();
        return markovClustering.getClusters();

    }

}
