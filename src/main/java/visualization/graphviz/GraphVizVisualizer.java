package visualization.graphviz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.Table;
import visualization.DotFormatGenerator;

public class GraphVizVisualizer<V> {

    public void generateGraphViz(final Table<V, V, Integer> clusters, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final GraphViz graphViz = new GraphViz();
        graphViz.addln(graphViz.start_graph());
        graphViz.addln("layout=\"sfdp\"");
        graphViz.addln("nodesep=\"3\"");
        graphViz.addln("ranksep=\"3\"");
        graphViz.addln("size=\"5000,5000\"");
        graphViz.addln(" label=\"" + folder + "\";");
        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 2) {

                try {
                    String row = cell.getRowKey().toString().substring(cell.getRowKey().toString().lastIndexOf("/") + 1, cell.getRowKey().toString().lastIndexOf("."));
                    String column = cell.getColumnKey().toString().substring(cell.getColumnKey().toString().lastIndexOf("/") + 1, cell.getColumnKey().toString().lastIndexOf("."));
                    graphViz.addln(row + "->" + column + "[ label = \"" + cell.getValue() + "\"]");
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println(cell.getRowKey());
                }

            }
        }
        graphViz.addln(graphViz.end_graph());
        graphViz.decreaseDpi();
        graphViz.decreaseDpi();
        File out = new File(folder + "/" + graphViz.getImageDpi() + ".svg");
        PrintWriter writer = new PrintWriter(folder + "/graphVizApp.dot", "UTF-8");
        writer.println(graphViz.getDotSource());
        writer.close();
        graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), "svg", "sfdp"), out);
    }

    public void generateGraphVizForCluster(final Table<V, V, Integer> clusters, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final GraphViz graphViz = new GraphViz();
        graphViz.addln(graphViz.start_graph());
        graphViz.addln("layout=\"sfdp\"");
        graphViz.addln("nodesep=\"3\"");
        graphViz.addln("ranksep=\"3\"");
        graphViz.addln("size=\"5000,5000\"");
        graphViz.addln(" label=\"" + folder + "\";");
        graphViz.addln("graph [ overlap=false ]\n" +
                "overlap = scale;");
        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 1) {
                String row = cell.getRowKey().toString().substring(cell.getRowKey().toString().lastIndexOf('/') + 1);
                String column = cell.getColumnKey().toString().substring(cell.getColumnKey().toString().lastIndexOf('/') + 1);
                graphViz.addln(row + "->" + column + "[ label = \"" + cell.getValue() + "\"]");
            }
        }
        graphViz.addln(graphViz.end_graph());
        graphViz.decreaseDpi();
        graphViz.decreaseDpi();
        File out = new File(folder + "/" + graphViz.getImageDpi() + ".svg");
        PrintWriter writer = new PrintWriter(folder + "/graphVizClusterClusters.dot", "UTF-8");
        writer.println(graphViz.getDotSource());
        writer.close();
        graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), "svg", "sfdp"), out);


    }

}
