package visualization.graphviz;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

public class GraphVizVisualizerTest {

    private GraphVizVisualizer graphVizVisualizer = new GraphVizVisualizer();

    @Test
    public void generateWithEmptyTable() throws FileNotFoundException, UnsupportedEncodingException {
        final Table<String, String, Integer> clusters = HashBasedTable.create();
        final String folder = "name.dot";

        graphVizVisualizer.generate(clusters, folder);
        assertTrue(new File(folder).exists());
    }

    @Test
    public void generateWithTable() throws IOException {
        String expected = "digraph G {\n" +
                "layout=\"sfdp\"\n" +
                " label=\"name.dot\";\n" +
                "graph [ overlap=false ]\n" +
                "overlap = scale;\n" +
                "\"One\"->\"Two\"[ label = \"7\"]\n" +
                "\"One\"->\"Three\"[ label = \"9\"]\n" +
                "\"Two\"->\"Three\"[ label = \"8\"]\n" +
                "}\n\r\n";

        System.out.println(expected);
        final Table<String, String, Integer> clusters = HashBasedTable.create();
        clusters.put("One", "Two", 7);
        clusters.put("Two", "Three", 8);
        clusters.put("One", "Three", 9);
        final String folder = "name.dot";


        graphVizVisualizer.generate(clusters, folder);
        String actual = Files.readString(Path.of(folder), StandardCharsets.UTF_8);



        assertEquals(expected,actual);
    }


}