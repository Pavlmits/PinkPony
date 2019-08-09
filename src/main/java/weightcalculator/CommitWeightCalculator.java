package weightcalculator;

import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import model.Commit;

public class CommitWeightCalculator implements WeightCalculator<String, Commit> {

    @Override
    public Table<String, String, Integer> calculate(final Set<String> vertices, final List<Commit> possibleEdges) {
        //Table that I wll save the result
        final Table<String, String, Integer> fileTable = HashBasedTable.create();
        // the set of vertices contains the files
        for (final String file : vertices) {
            //possible edges are the commits list
            for (final Commit commit : possibleEdges) {
                if (commit.getPaths().contains(file)) {
                    //commit.getPaths() returns the files that touched in the current commit
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
        System.out.println("Weighted table has calculated");
        return cutSmallWeights(fileTable);
    }

    private  Table<String, String, Integer> cutSmallWeights(final Table<String, String, Integer> table){
        final Table<String, String, Integer> fileTable = HashBasedTable.create();
        for(Table.Cell cell: table.cellSet()){
            int value = (int) cell.getValue();
            //TODO change number of files
            if(value > 1){
                fileTable.put(cell.getRowKey().toString(), cell.getColumnKey().toString(),(Integer) cell.getValue());
            }
        }
        return fileTable;
    }
}
