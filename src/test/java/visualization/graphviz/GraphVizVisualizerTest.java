package visualization.graphviz;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

public class GraphVizVisualizerTest {

    private GraphVizVisualizer graphVizVisualizer = new GraphVizVisualizer();

    @Test
    public void generateWithEmptyTable() throws FileNotFoundException, UnsupportedEncodingException {
        final Table<String, String, Integer> clusters = HashBasedTable.create();
        final String folder = "folder";

        graphVizVisualizer.generate(clusters, folder);


    }

}