package weightcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Package;
import model.Commit;

public class ClusterWeightCalculator implements WeightCalculator<Package, Commit> {

    @Override
    public Table<Package, Package, Integer> calculate(final Set<Package> vertices, final List<Commit> possibleEdges) {
        final Table<Package, Package, Integer> clusterTable = HashBasedTable.create();
        final List<Package> verticesList = new ArrayList<>(vertices);
        for (final Commit commit : possibleEdges) {
            for (int i = 0; i < verticesList.size(); i++) {
                for (int j = i + 1; j < verticesList.size(); j++) {
                    final int weightTemp = calculateBetweenClusterWeight(verticesList.get(i), verticesList.get(j), commit);
                    if (clusterTable.contains(verticesList.get(i), verticesList.get(j))) {
                        clusterTable.put(verticesList.get(i), verticesList.get(j), clusterTable.get(verticesList.get(i), verticesList.get(j)) + weightTemp);
                    } else {
                        clusterTable.put(verticesList.get(i), verticesList.get(j), weightTemp);
                    }
                }
            }
        }

        return clusterTable;
    }


    private int calculateBetweenClusterWeight(final Package package1, final Package package2, Commit commit) {
        for (String s1 : package1.getFiles()) {
            for (String s2 : package2.getFiles()) {
                if (commit.getPaths().contains(s1) && commit.getPaths().contains(s2)) {
                    return 1;
                }
            }

        }
        return 0;
    }
}
