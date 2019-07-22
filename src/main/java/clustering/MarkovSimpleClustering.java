package clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Table;
import graph.GraphCreator;
import model.Cluster;
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

    public MarkovSimpleClustering(final int e, final double r, final GraphCreator<V> graphCreator) {
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

    public List<Cluster<V>> clusterCustom(final Table<V, V, Integer> matrix) {
        //TODO create my Markov clustering algo
        return new ArrayList<>();
    }

    private Table<V, V, Double> normalize(final Table<V, V, Double> matrix) {
        for (V col : matrix.columnKeySet()) {
            double colSum = 0;
            for (V row : matrix.rowKeySet()) {
                colSum += matrix.get(row, col);
            }
            if (colSum == 0)
                continue;
            for (V row : matrix.rowKeySet()) {
                Double value = matrix.get(row, col);
                matrix.put(row, col, value / colSum);
            }
        }
        return matrix;
    }

}
