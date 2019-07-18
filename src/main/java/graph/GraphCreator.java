package graph;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import weightCalculator.WeightCalculator;

/**
 * Creates a weighted graph
 * V is the type vertices
 * T is the type of object that we will extract the edges
 *
 * @author pmitsou
 * @version 15/07/2019
 */
public class GraphCreator<T, V> {

    private final WeightCalculator<T, V> weightCalculator;

    @Inject
    public GraphCreator(final WeightCalculator<T, V> weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

    /**
     * Creates a undirected weighted graph
     *
     * @param vertices
     * @param possibleEdges
     * @return wighted graph
     */
    public Graph<V, DefaultWeightedEdge> createSimpleWightedGraph(final Set<V> vertices, final List<T> possibleEdges) {
        final Graph<V, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (V file : vertices) {
            graph.addVertex(file);
        }
        final Table<V, V, Integer> fileTable = weightCalculator.calculate(vertices, possibleEdges);
        for (Table.Cell<V, V, Integer> cell : fileTable.cellSet()) {
            DefaultWeightedEdge weightedEdge = graph.addEdge(cell.getRowKey(), cell.getColumnKey());
            if (weightedEdge != null && cell.getValue() > 1) {
                graph.setEdgeWeight(weightedEdge, cell.getValue());
            }
        }
        return graph;
    }

    public MutableValueGraph<V, Integer> create(final Set<V> vertices, final List<T> possibleEdges) {
        final MutableValueGraph<V, Integer> weightedGraph = ValueGraphBuilder.undirected().build();
        for (V file : vertices) {
            weightedGraph.addNode(file);
        }
        final Table<V, V, Integer> fileTable = weightCalculator.calculate(vertices, possibleEdges);
        for (Table.Cell<V, V, Integer> cell : fileTable.cellSet()) {
            if (cell.getValue() > 2) {
                weightedGraph.putEdgeValue(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        }

        return weightedGraph;
    }

}
