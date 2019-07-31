package clustering;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.nlpub.watset.graph.ChineseWhispers;
import org.nlpub.watset.graph.NodeWeighting;
import org.nlpub.watset.graph.Watset;
import org.nlpub.watset.util.CosineContextSimilarity;
import org.nlpub.watset.wsi.Sense;

public class WatsetClustering<V> implements Clustering<V> {


    private final GraphCreator<V> graphCreator;

    public WatsetClustering(final GraphCreator<V> graphCreator) {
        this.graphCreator = graphCreator;
    }

    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final Random random = new Random(1337);
        final Function<Graph<V, DefaultWeightedEdge>, org.nlpub.watset.graph.Clustering<V>> local = ChineseWhispers.provider(NodeWeighting.top(), ChineseWhispers.ITERATIONS, random);
        final Function<Graph<Sense<V>, DefaultWeightedEdge>, org.nlpub.watset.graph.Clustering<Sense<V>>> global = ChineseWhispers.provider(NodeWeighting.top(), ChineseWhispers.ITERATIONS, random);
        final Watset<V, DefaultWeightedEdge> watset = new Watset<>(graphCreator.createSimpleWightedGraph(fileTable), local, global, new CosineContextSimilarity<>());
        watset.fit();
        return watset.getClusters();
    }
}
