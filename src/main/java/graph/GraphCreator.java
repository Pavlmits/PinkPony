package graph;

import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Creates a weighted graph
 * V is the type vertices
 * T is the type of object that we will extract the edges
 *
 * @author pmitsou
 * @version 15/07/2019
 */
public class GraphCreator<V> {

    /**
     * Creates a jgrapht undirected weighted graph
     *
     * @return wighted graph
     */
    public Graph<V, DefaultWeightedEdge> createSimpleWightedGraph(final Table<V, V, Integer> fileTable) {
        final Graph<V, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (V file : fileTable.rowKeySet()) {
            graph.addVertex(file);
        }
        for (Table.Cell<V, V, Integer> cell : fileTable.cellSet()) {
            DefaultWeightedEdge weightedEdge = graph.addEdge(cell.getRowKey(), cell.getColumnKey());
            if (weightedEdge != null && cell.getValue() > 1) {
                graph.setEdgeWeight(weightedEdge, cell.getValue());
            }
        }
        return graph;
    }

    /**
     * Creates a guava undirected weighted graph
     *
     * @return Mutable value graph from guava
     */
    public MutableValueGraph<V, Integer> create(final Table<V, V, Integer> fileTable) {
        final MutableValueGraph<V, Integer> weightedGraph = ValueGraphBuilder.undirected().build();
        for (V file : fileTable.rowKeySet()) {
            weightedGraph.addNode(file);
        }
        for (Table.Cell<V, V, Integer> cell : fileTable.cellSet()) {
            if (cell.getValue() > 2) {
                weightedGraph.putEdgeValue(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        }

        return weightedGraph;
    }

}
