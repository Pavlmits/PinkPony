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
            case "watset":
                return new WatsetClustering(graphCreator);
            case "ch":
                return new ChineseWhispersClustering(graphCreator);
            default:
                throw new UnknownParameterException("Unknown clustering parameter!");
        }
    }

    private ClusteringFactory() {
    }
}
