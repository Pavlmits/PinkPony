package clustering;

import exception.UnknownParameterException;
import graph.GraphCreator;

public class ClusteringFactory {

    public static Clustering getClustering(final String typeOfClustering, final GraphCreator graphCreator) throws UnknownParameterException {
        switch (typeOfClustering) {
            case "mr":
                return new MarkovSimpleClustering<>(2, 2, graphCreator);
            case "max":
                return new MaxMaxClustering<>(graphCreator);
            case  "kr":
                return  new KruskalsClustering(graphCreator, 3);
            default:
                throw new UnknownParameterException("Unknown clustering parameter!");
        }
    }
}
