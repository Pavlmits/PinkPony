package clusteringlevel;

import exception.UnknownParameterException;

public class ClusteringLevelFactory {

    public static ClusteringLevel getClusteringLevel(final String type) throws UnknownParameterException {
        switch (type) {
            case "file":
                return new FileLevel();
            case "pack":
                return new PackageLevel();
            default:
                throw new UnknownParameterException("Unknown clustering level");

        }
    }
}
