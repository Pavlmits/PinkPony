package filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class GraphEdgeFilter<V> {

    public Table<V,V,Integer> removeEdges(final Table<V,V,Integer> table, final Collection<Collection<V>> clusters){
        final Table<V,V ,Integer> filteredTable = HashBasedTable.create();
        final List<List<V>> clusterList = new ArrayList<>();
        clusters.forEach(c -> clusterList.add(new ArrayList<>(c)));

        for (final List<V> list : clusterList) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i; j < list.size(); j++) {
                    if(table.contains(list.get(i), list.get(j))){
                        filteredTable.put(list.get(i), list.get(j), table.get(list.get(i), list.get(j)));
                    }
                }
            }
        }
        return filteredTable;
    }
}
