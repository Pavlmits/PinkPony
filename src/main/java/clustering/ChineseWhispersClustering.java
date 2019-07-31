package clustering;

import java.util.Collection;
import java.util.Random;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.nlpub.watset.graph.ChineseWhispers;
import org.nlpub.watset.graph.NodeWeighting;

public class ChineseWhispersClustering<V> implements Clustering<V>{

    private final GraphCreator<V> graphCreator;

    public ChineseWhispersClustering(final GraphCreator<V> graphCreator) {
        this.graphCreator = graphCreator;
    }

    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final Random random = new Random(1337);
        ChineseWhispers<V, DefaultWeightedEdge> cw1 = new ChineseWhispers<>(graphCreator.createSimpleWightedGraph(fileTable), NodeWeighting.top(), ChineseWhispers.ITERATIONS, random);
        cw1.fit();
        return cw1.getClusters();
    }
}
