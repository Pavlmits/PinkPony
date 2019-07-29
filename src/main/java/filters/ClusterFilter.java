package filters;

import java.util.ArrayList;
import java.util.Collection;

public class ClusterFilter<T> {

    private static final int MAX_FILES_IN_A_PACKAGE = 15;

    public Collection<Collection<T>> filter(final Collection<Collection<T>> clusters){
        final Collection<Collection> filteredClusters = new ArrayList<>();

        for (final Collection<T> cluster : clusters) {
            if(cluster.size() > MAX_FILES_IN_A_PACKAGE){
                filteredClusters.add(cluster);
            }
        }
        clusters.removeAll(filteredClusters);
        return clusters;
    }
}
