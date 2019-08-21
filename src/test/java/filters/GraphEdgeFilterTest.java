package filters;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import clustering.Clustering;
import clustering.ClusteringFactory;
import com.google.common.collect.Table;
import exception.UnknownParameterException;
import graph.GraphCreator;
import org.junit.Ignore;
import org.junit.Test;
import util.FileHandler;

public class GraphEdgeFilterTest {

    @Ignore
    @Test
    public void removeEdgesTest() throws IOException, UnknownParameterException {
        GraphEdgeFilter<String> graphEdgeFilter = new GraphEdgeFilter<>();
        final Table<String, String, Integer> table = FileHandler.readTable("table1.txt");
        final Clustering clustering = ClusteringFactory.getClustering("mr", new GraphCreator());
        final Collection<Collection<String>> clusterMr = clustering.cluster(table);

        final Table<String, String, Integer> actual = graphEdgeFilter.removeEdges(table, clusterMr);

        assertEquals(graphEdgeFilter.removeEdges(table, clusterMr), actual);
    }
}