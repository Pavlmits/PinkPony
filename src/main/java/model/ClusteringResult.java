package model;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Table;
import lombok.Getter;

@Getter
public class ClusteringResult<V> {

    private final Table<V,V,Integer> weightTable;

    private final Collection<Collection<V>> clusters;

    private final Set<V> files;

    public ClusteringResult(final Table<V, V, Integer> weightTable, final Collection<Collection<V>> clusters, final Set<V> files) {
        this.weightTable = weightTable;
        this.clusters = clusters;
        this.files = files;
    }
}
