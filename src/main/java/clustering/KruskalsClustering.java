package clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Implementation of Kruskal'a algorithm
 *
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
        final Map<Integer, List<V>> clusters = new HashMap<>();
        final Graph<V, DefaultWeightedEdge> simpleWightedGraph = graphCreator.createSimpleWightedGraph(fileTable);
        final SpanningTreeAlgorithm.SpanningTree<DefaultWeightedEdge> spanningTree = new KruskalMinimumSpanningTree<>(simpleWightedGraph).getSpanningTree();
//        final Set<DefaultWeightedEdge> edges = spanningTree.getEdges();
//        for (final DefaultWeightedEdge edge : edges) {
//            simpleWightedGraph.getEdgeWeight(edge);
//        }
        final Set<V> vs = fileTable.rowKeySet();

        int i = 0;
        for (V v : fileTable.rowKeySet()) {
            clusters.put(i, new ArrayList<>());
            clusters.get(i).add(v);
            i++;
        }

        return Collections.emptyList();
    }

    private void createCluster(V v, int cluster) {

    }

//    private void clustering(final Table<V, V, Integer> fileTable, int k) {
//        final Queue<V> mst = new Queue<V>();
//        PriorityQueue<V> pq = new MinPriorityQueue<V>();
//        for (V e : .edges()) {
//            pq.insert(e);
//        }
    //}
}
