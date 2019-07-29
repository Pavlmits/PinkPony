package clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;
import model.Cluster;

public class ABCDClustering<V> implements Clustering<V> {


    @Override
    public Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable) {
        final List<Cluster<V>> initialClusters = createInitialClusters(fileTable);

        return null;
    }

    private List<Cluster<V>> createInitialClusters(final Table<V, V, Integer> fileTable) {
        final List<Cluster<V>> clusters = new ArrayList<>();
        for (V vertex : fileTable.rowKeySet()) {
            final List<V> list = new ArrayList<>();
            list.add(vertex);
            clusters.add(new Cluster<>(list));
        }
        return clusters;
    }

    private int[][] calculateAttractivenessMatrix(final Table<V, V, Integer> fileTable, final Cluster<V> cluster) {
        return new int[0][0];
    }

    private double calculateAttractiveness(final Table<V, V, Integer> fileTable, V vertex1, V vertex2) {
        int sumWeight = 0;
        final Set<Map.Entry<V, Integer>> mapVertex1 = fileTable.row(vertex1).entrySet();
        final Set<Map.Entry<V, Integer>> mapVertex2 = fileTable.row(vertex2).entrySet();
        for (Map.Entry<V, Integer> entry : mapVertex1) {
            if (vertex2.equals(entry.getKey())) {
                sumWeight += entry.getValue();
            }
        }

        return sumWeight / (mapVertex1.size() + mapVertex2.size());
    }

    private double densityCalculator(final Table<V, V, Integer> fileTable, V vertex){
        int sum = 0;
        final Set<Map.Entry<V, Integer>> entries = fileTable.row(vertex).entrySet();
        for (final Map.Entry<V, Integer> entry : entries) {
            sum += entry.getValue();
        }
        return (double) sum/entries.size();
    }
}
