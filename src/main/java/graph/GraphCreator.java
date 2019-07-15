package graph;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import weightCalculator.WeightCalculator;

/**
 * Creates a weighted graph
 * S is the type vertices
 * T is the type of object that we will extract the edges
 *
 * @author pmitsou
 * @version 15/07/2019
 */
public class GraphCreator<T, S> {

    private final WeightCalculator<T, S> weightCalculator;

    public GraphCreator(final WeightCalculator<T, S> weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

    /**
     * Creates a undirected weighted graph
     * @param vertices
     * @param possibleEdges
     * @return wighted graph
     */
    public Graph<S, DefaultWeightedEdge> createSimpleWightedGraph(final Set<S> vertices, final List<T> possibleEdges) {
        final Graph<S, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (S file : vertices) {
            graph.addVertex(file);
        }
        final Table<S, S, Integer> fileTable = weightCalculator.calculate(vertices, possibleEdges);
        for (Table.Cell<S, S, Integer> cell : fileTable.cellSet()){
            DefaultWeightedEdge weightedEdge = graph.addEdge(cell.getRowKey(), cell.getColumnKey());
            if( weightedEdge != null && cell.getValue() > 1){
                graph.setEdgeWeight(weightedEdge, cell.getValue());
            }
        }
        return graph;
    }


    public MutableValueGraph<S, Integer> create(final Set<S> vertices, final List<T> possibleEdges){
        final MutableValueGraph<S, Integer> weightedGraph = ValueGraphBuilder.undirected().build();
        for (S file : vertices) {
            weightedGraph.addNode(file);
        }
        final Table<S, S, Integer> fileTable = weightCalculator.calculate(vertices, possibleEdges);
        for (Table.Cell<S, S, Integer> cell : fileTable.cellSet()){
            if(cell.getValue() > 3){
                weightedGraph.putEdgeValue(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        }

        return weightedGraph;
    }

}
