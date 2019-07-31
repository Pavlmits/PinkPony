package clustering;

import java.util.Collection;

import com.google.common.collect.Table;


public interface Clustering<V> {

    Collection<Collection<V>> cluster(final Table<V, V, Integer> fileTable);

}
