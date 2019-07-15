package weightCalculator;

import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Commit;

public class CommitWeightCalculator implements WeightCalculator<Commit, String> {

    @Override
    public Table<String, String, Integer> calculate(final Set<String> vertices, final List<Commit> possibleEdges) {
        final Table<String, String, Integer> fileTable = HashBasedTable.create();
        for (final String file : vertices) {
            for (final Commit commit : possibleEdges) {
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
