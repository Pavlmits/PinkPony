package weightcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Commit;
import model.Package;

public class ClusterWeightCalculator implements WeightCalculator<Package, Commit> {

    @Override
    public Table<Package, Package, Integer> calculate(final Set<Package> vertices, final List<Commit> possibleEdges) {
        final Table<Package, Package, Integer> clusterTable = HashBasedTable.create();
        final List<Package> verticesList = new ArrayList<>(vertices);
        int count = 0;
        for (final Commit commit : possibleEdges) {
            System.out.println(count);
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
            count++;
        }

        return clusterTable;
    }


    private int calculateBetweenClusterWeight(final Package package1, final Package package2, Commit commit) {
        return !Collections.disjoint(package1.getFiles(), commit.getPaths()) && !Collections.disjoint(package2.getFiles(), commit.getPaths()) ? 1 : 0;
    }
}
