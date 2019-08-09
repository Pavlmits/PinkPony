package weightcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Cluster;
import model.Commit;

public class ClusterWeightCalculator implements WeightCalculator<Cluster, Commit> {

    @Override
    public Table<Cluster, Cluster, Integer> calculate(final Set<Cluster> vertices, final List<Commit> possibleEdges) {
        final Table<Cluster, Cluster, Integer> clusterTable = HashBasedTable.create();
        final List<Cluster> verticesList = new ArrayList<>(vertices);
        for (int i = 0; i < verticesList.size(); i++) {
            for (int j = i; j < verticesList.size(); j++) {
                clusterTable.put(verticesList.get(i), verticesList.get(j),
                        calculateBetweenClusterWeight(verticesList.get(i), verticesList.get(j), possibleEdges));
            }
        }

        return clusterTable;
    }


    private int calculateBetweenClusterWeight(final Cluster cluster1, final Cluster cluster2, final List<Commit> possibleEdges) {
        int count = 0;
        for (final Commit commit : possibleEdges) {
            for (String s1 : cluster1.getFiles()) {
                for (String s2 : cluster2.getFiles()) {
                    if (commit.getPaths().contains(s1) && commit.getPaths().contains(s2)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
