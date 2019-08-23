import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import clustering.Clustering;
import clustering.ClusteringFactory;
import com.google.common.collect.Table;
import exception.UnknownParameterException;
import filters.GraphEdgeFilter;
import graph.GraphCreator;
import util.FileHandler;
import visualization.graphviz.GraphVizVisualizer;

public class ClusterClusters {

    public static void main(String[] args) throws IOException, UnknownParameterException {
        final Table<String, String, Integer> table = FileHandler.readTable(new FileInputStream(new File("table1.txt")));
        final Clustering max = ClusteringFactory.getClustering("max", new GraphCreator());
        final Clustering mr = ClusteringFactory.getClustering("mr", new GraphCreator());
        final Clustering watset = ClusteringFactory.getClustering("watset", new GraphCreator());
        final Clustering ch = ClusteringFactory.getClustering("ch", new GraphCreator());

        final Collection<Collection<String>> clusterMax = max.cluster(table);
        final Collection<Collection<String>> clusterMr = mr.cluster(table);
        final Collection<Collection<String>> clusterWatset = watset.cluster(table);
        final Collection<Collection<String>> clusterCh = ch.cluster(table);

        final GraphEdgeFilter<String> graphEdgeFilter = new GraphEdgeFilter<>();
        final Table<String, String, Integer> filteredTableMr = graphEdgeFilter.removeEdges(table, clusterMr);
        final Table<String, String, Integer> filteredTableMax = graphEdgeFilter.removeEdges(table, clusterMax);
        final Table<String, String, Integer> filteredTableWatset = graphEdgeFilter.removeEdges(table, clusterWatset);
        final Table<String, String, Integer> filteredTableCh = graphEdgeFilter.removeEdges(table, clusterCh);


        final FileHandler<String> fileHandler = new FileHandler<>();
        fileHandler.export(clusterMax, "data/max.txt");
        fileHandler.export(clusterMr, "data/markov.txt");
        fileHandler.export(clusterWatset, "data/watset.txt");
        fileHandler.export(clusterCh, "data/chineseWhispers.txt");
        fileHandler.exportWithClusterPrefix(clusterMr, "data/markovC.csv");
        fileHandler.exportWithClusterPrefix(clusterMax, "data/maxC.csv");
        fileHandler.exportWithClusterPrefix(clusterWatset, "data/watsetC.csv");
        fileHandler.exportWithClusterPrefix(clusterCh, "data/chineseC.csv");
        final GraphVizVisualizer<String> graphVizVisualizer = new GraphVizVisualizer();
        graphVizVisualizer.generate(filteredTableMax, "data/max.dot");
        graphVizVisualizer.generate(filteredTableMr, "data/mr.dot");
        graphVizVisualizer.generate(filteredTableWatset, "data/watset.dot");
        graphVizVisualizer.generate(filteredTableCh, "data/ch.dot");

    }
}