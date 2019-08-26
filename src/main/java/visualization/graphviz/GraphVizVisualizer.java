package visualization.graphviz;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.Table;

public class GraphVizVisualizer<V> {


    public void generate(final Table<V, V, Integer> clusters, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        final String graphFormat =
                buildGraphHeader(fileName)
                        + buildGraphBody(clusters)
                        + buildGraphFooter();
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(graphFormat);
        writer.close();
    }

    private String buildGraphHeader(final String fileName) {
        final String header = "digraph G {\n" +
                "layout=\"sfdp\"\n" +
                " label=\"" + fileName + "\";\n" +
                "graph [ overlap=false ]\n" +
                "overlap = scale;\n";
        return header;
    }

    private String buildGraphBody(final Table<V, V, Integer> clusters) {
        String body = "";
        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 3) {
                body += "\"" + cell.getRowKey().toString() + "\"" + "->" + "\""
                        + cell.getColumnKey().toString() + "\"" + "[ label = \""
                        + cell.getValue() + "\"]\n";
            }
        }
        return body;
    }

    private String buildGraphFooter() {
        final String footer = "}\n\r\n";
        return footer;
    }

}
