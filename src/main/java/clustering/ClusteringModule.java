package clustering;

import dagger.Module;
import dagger.Provides;

@Module
class ClusteringModule {

    @Provides
    Clustering provideWatsetClustering(final WatsetClustering clustering){
        return clustering;
    }
}
