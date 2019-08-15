package visualization.graphviz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;

public class GraphVizVisualizer<V> {


    public void generate(final Table<V, V, Integer> clusters, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final GraphViz graphViz = new GraphViz();
        graphViz.addln(graphViz.start_graph());
        graphViz.addln("layout=\"sfdp\"");
        graphViz.addln(" label=\"" + folder + "\";");
        graphViz.addln("graph [ overlap=false ]\n" +
                "overlap = scale;");
        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 1) {
                graphViz.addln("\"" + cell.getRowKey().toString() + "\"" + "->" + "\"" + cell.getColumnKey().toString() + "\"" + "[ label = \"" + cell.getValue() + "\"]");
            }
        }
        graphViz.addln(graphViz.end_graph());
        graphViz.decreaseDpi();
        graphViz.decreaseDpi();
        File out = new File(folder + "/" + graphViz.getImageDpi() + ".svg");
        PrintWriter writer = new PrintWriter(folder + "/graphVizFile.dot", "UTF-8");
        writer.println(graphViz.getDotSource());
        writer.close();
        graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), "svg", "sfdp"), out);


    }

    public void generateCluster(final List<Collection<V>> clusterListOfCollections, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph G {\n");
        int count = 0;
        for (final Collection<V> cluster : clusterListOfCollections) {
            generatedText.append("subgraph run").append(count).append("{\n");
            final List<V> clustersList = new ArrayList<>(cluster);
            final Set<String> connections = new HashSet<>();
            for (V acluster : clustersList) {
                connections.add("\"" + acluster + "\"" + ";\n");

            }
            connections.forEach(s -> generatedText.append(s));
            generatedText.append("}\n");
            count++;
        }
        generatedText.append("}");
        PrintWriter writer = new PrintWriter(folder + "/graphVizSubGraph.dot", "UTF-8");
        writer.println(generatedText);
        writer.close();

    }

}
