package clustering;

import static junit.framework.TestCase.assertNotNull;

import java.util.Collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import graph.GraphCreator;
import org.junit.Test;

public class ChineseWhispersClusteringTest {

    private GraphCreator<String> graphCreator = new GraphCreator<>();

    private ChineseWhispersClustering<String> clustering = new ChineseWhispersClustering<>(graphCreator);


    @Test
    public void clusterTest() {
        final Table<String, String, Integer> table = HashBasedTable.create();

        final Collection<Collection<String>> clusters = clustering.cluster(table);
        assertNotNull(clusters);
    }

}