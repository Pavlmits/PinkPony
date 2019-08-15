package clusteringlevel;

import exception.UnknownParameterException;
import filters.FilesFilter;
import graph.GraphCreator;
import weightcalculator.ClusterWeightCalculator;
import weightcalculator.CommitWeightCalculator;

public class ClusteringLevelFactory {

    public static ClusteringLevel getClusteringLevel(final String type) throws UnknownParameterException {
        switch (type) {
            case "file":
                return new FileLevel(new FilesFilter(), new GraphCreator(), new CommitWeightCalculator());
            case "pack":
                return new PackageLevel(new GraphCreator(), new ClusterWeightCalculator());
            default:
                throw new UnknownParameterException("Unknown clustering level");

        }
    }

    private ClusteringLevelFactory() {
    }
}
