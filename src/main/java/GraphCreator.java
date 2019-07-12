import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Commit;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GraphCreator {

    public Graph<String, DefaultWeightedEdge> create(final Set<String> fileSet, final List<Commit> commitList) {
        final Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (String file : fileSet) {
            graph.addVertex(file);
        }

        final Table<String, String, Integer> fileTable = weightCalculator(fileSet, commitList);
        for (Table.Cell<String, String, Integer> cell : fileTable.cellSet()){
            DefaultWeightedEdge weightedEdge = graph.addEdge(cell.getRowKey(), cell.getRowKey());
            graph.setEdgeWeight(weightedEdge, cell.getValue());
        }
        return graph;
    }

    public Table<String, String, Integer> weightCalculator(final Set<String> fileSet, final List<Commit> commitList) {
        final Table<String, String, Integer> fileTable = HashBasedTable.create();
        for (final String file : fileSet) {
            for (final Commit commit : commitList) {
                if (commit.getPaths().contains(file)) {
                    for (String s : commit.getPaths()) {
                        if (!file.equals(s)) {
                            if (!fileTable.contains(file, s)) {
                                fileTable.put(file, s, 1);
                            } else {
                                fileTable.put(file, s, fileTable.get(file, s) + 1);
                            }
                        }
                    }
                }
            }
        }
        return fileTable;
    }
}
