package clustering;

import java.util.Collection;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Chameleon<V> implements Clustering<V>{


    private final GraphCreator<V> graphCreator;

    public Chameleon(final GraphCreator<V> graphCreator) {
        this.graphCreator = graphCreator;
    }

    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        //first part : the sparse graph == co-change graph
        final Graph<V, DefaultWeightedEdge> simpleWightedGraph = graphCreator.createSimpleWightedGraph(fileTable);
        //second part: partition algorithm

        return null;
    }

    private void partitionCluster(){

    }
}
