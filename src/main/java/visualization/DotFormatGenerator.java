package visualization;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import model.Package;

public class DotFormatGenerator<V> {

    public void generate(final Table<V, V, Integer> clusters, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph {\nratio=0.3\nsize=\"10,7\";\n");
        generatedText.append(" label=\"").append(folder).append("\";");
        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 2) {
                try {
                    String row = cell.getRowKey().toString().substring(cell.getRowKey().toString().lastIndexOf('/') + 1, cell.getRowKey().toString().lastIndexOf('.'));
                    String column = cell.getColumnKey().toString().substring(cell.getColumnKey().toString().lastIndexOf('/') + 1, cell.getColumnKey().toString().lastIndexOf('.'));
                    //TODO find better way to remove the spaces
                    if (!row.contains(" ") || !column.contains(" ")) {
                        generatedText.append(row)
                                .append(" -> ")
                                .append(column)
                                .append(" [ label = \"")
                                .append(cell.getValue()).append("\" len=0.1 ];\n");
                    }

                } catch (StringIndexOutOfBoundsException e) {

                }
            }
        }
        generatedText.append("}");
        PrintWriter writer = new PrintWriter(folder + "/graphViz.dot", "UTF-8");
        writer.println(generatedText);
        writer.close();
    }

//    public void generateForPackage(final Collection<Collection<Package>> clusters, final String folder) {
//        final StringBuilder generatedText = new StringBuilder();
//        generatedText.append("digraph {\nratio=0.3\nsize=\"10,7\";\n");
//        generatedText.append(" label=\"").append(folder).append("\";");
//        for (Table.Cell<V, V, Integer> cell : clusters.cellSet()) {
//        }
//    }

    public void subgraphCluster(final List<Collection<V>> clusterListOfCollections, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph G {\n");
        int count = 0;
        for (final Collection<V> cluster : clusterListOfCollections) {
            generatedText.append("subgraph cluster").append(count).append("{\n");
            final List<V> clustersList = new ArrayList<>(cluster);
            final Set<String> connections = new HashSet<>();
            for (V aClustersList : clustersList) {
                final String item = aClustersList.toString().substring(aClustersList.toString().lastIndexOf('/') + 1);
                connections.add(item + ";\n");

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