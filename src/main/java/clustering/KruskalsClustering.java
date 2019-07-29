package clustering;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Table;
import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Implementation of Kruskal'a algorithm
 * @param <V> is the type of vertices
 */
public class KruskalsClustering<V> implements Clustering<V> {

    private final GraphCreator<V> graphCreator;

    // desired number of clusters.
    private final int k;

    public KruskalsClustering(final GraphCreator<V> graphCreator, final int k) {
        this.graphCreator = graphCreator;
        this.k = k;
    }

    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final Graph<V, DefaultWeightedEdge> simpleWightedGraph = graphCreator.createSimpleWightedGraph(fileTable);
        final SpanningTreeAlgorithm.SpanningTree<DefaultWeightedEdge> spanningTree = new KruskalMinimumSpanningTree<>(simpleWightedGraph).getSpanningTree();
        final Set<DefaultWeightedEdge> edges = spanningTree.getEdges();
        for (final DefaultWeightedEdge edge : edges) {
            simpleWightedGraph.getEdgeWeight(edge);
        }

        return Collections.emptyList();
    }

    private void createCluster(V v, int cluster){

    }
}
