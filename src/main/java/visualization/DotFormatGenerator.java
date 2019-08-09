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

public class DotFormatGenerator {

    public void generate(final Table<String, String, Integer> clusters) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph {\nratio=0.3\nsize=\"10,7\";\n");
        for (Table.Cell<String, String, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 2) {
                try {
                    String row = cell.getRowKey().substring(cell.getRowKey().lastIndexOf('/') + 1, cell.getRowKey().lastIndexOf('.'));
                    String column = cell.getColumnKey().substring(cell.getColumnKey().lastIndexOf('/') + 1, cell.getColumnKey().lastIndexOf('.'));
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
        PrintWriter writer = new PrintWriter("graphViz.dot", "UTF-8");
        writer.println(generatedText);
        writer.close();
    }

    public void generateSubclusters(final Table<String, String, Integer> table, final List<Collection<String>> clusterList) {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph {\nratio=0.3\nsize=\"10,7\";\n");
        for (Table.Cell<String, String, Integer> cell : table.cellSet()) {
            if (cell.getValue() > 2) {
                try {
                    String row = cell.getRowKey().substring(cell.getRowKey().lastIndexOf('/') + 1, cell.getRowKey().lastIndexOf('.'));
                    String column = cell.getColumnKey().substring(cell.getColumnKey().lastIndexOf('/') + 1, cell.getColumnKey().lastIndexOf('.'));
                    generatedText.append(row)
                            .append(" -> ")
                            .append(column)
                            .append(";\n");
                } catch (StringIndexOutOfBoundsException e) {

                }

            }
        }
        generatedText.append("}");

    }

    public void subgraph(final List<Collection<String>> clusterList, final Table<String, String, Integer> table) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("digraph G {\n");
        int count = 0;
        for (final Collection<String> strings : clusterList) {
            generatedText.append("subgraph cluster").append(count).append("{\n");
            final List<String> stringList = new ArrayList<>(strings);
            final Set<String> connections = new HashSet<>();
            for (int i = 0; i < stringList.size(); i++) {
                for (int j = 0; j < stringList.size(); j++) {
                    final String first = stringList.get(i).substring(stringList.get(i).lastIndexOf('/') + 1, stringList.get(i).lastIndexOf('.'));
                    final String second = stringList.get(j).substring(stringList.get(j).lastIndexOf('/') + 1, stringList.get(j).lastIndexOf('.'));
                    connections.add(first + " -> " + second + "\n");
                }
            }
            connections.forEach(s -> generatedText.append(s));
            generatedText.append("}\n");
            count++;
        }
        generatedText.append("}");
        PrintWriter writer = new PrintWriter("graphVizSubGraph.dot", "UTF-8");
        writer.println(generatedText);
        writer.close();

    }
}