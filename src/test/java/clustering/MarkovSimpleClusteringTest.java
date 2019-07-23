package clustering;

import static junit.framework.TestCase.assertNotNull;

import java.util.Collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import graph.GraphCreator;
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

        final Collection<Collection<String>> clusters = clustering.cluster(table);
        assertNotNull(clusters);

    }

}