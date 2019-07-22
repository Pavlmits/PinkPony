package clustering;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MarkovSimpleClusteringTest {

    @Mock
    private GraphCreator<String> graphCreator = new GraphCreator<>();

    @InjectMocks
    private MarkovSimpleClustering<String> clustering = new MarkovSimpleClustering<>(0,0,graphCreator);



    @Test
    public void clusterTest(){
        final Table<String, String, Integer> table = HashBasedTable.create();

        when(graphCreator.createSimpleWightedGraph(table)).thenReturn(new SimpleDirectedGraph<>(DefaultWeightedEdge.class));
        final Collection<Collection<String>> clusters = clustering.cluster(table);
        assertNotNull(clusters);

    }

}