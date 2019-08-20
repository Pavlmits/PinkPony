package weightcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Commit;
import model.Module;

public class ModuleWeightCalculator implements WeightCalculator<Module, Commit> {

    @Override
    public Table<Module, Module, Integer> calculate(final Set<Module> vertices, final List<Commit> possibleEdges) {
        final Table<Module, Module, Integer> clusterTable = HashBasedTable.create();
        final List<Module> verticesList = new ArrayList<>(vertices);
        for (final Commit commit : possibleEdges) {
            for (int i = 0; i < verticesList.size(); i++) {
                for (int j = i + 1; j < verticesList.size(); j++) {
                    final int weightTemp = calculateBetweenClusterWeight(verticesList.get(i), verticesList.get(j), commit);
                    if (clusterTable.contains(verticesList.get(i), verticesList.get(j))) {
                        int value = clusterTable.get(verticesList.get(i), verticesList.get(j));
                        clusterTable.put(verticesList.get(i), verticesList.get(j), value + weightTemp);
                    } else {
                        clusterTable.put(verticesList.get(i), verticesList.get(j), weightTemp);
                    }
                }
            }
        }

        return clusterTable;
    }


    private int calculateBetweenClusterWeight(final Module module1, final Module module2, Commit commit) {
        return !Collections.disjoint(module1.getFiles(), commit.getPaths()) && !Collections.disjoint(module2.getFiles(), commit.getPaths()) ? 1 : 0;
    }
}
