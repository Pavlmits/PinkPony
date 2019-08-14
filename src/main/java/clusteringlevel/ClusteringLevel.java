package clusteringlevel;

import java.io.IOException;
import java.util.List;

import exception.UnknownParameterException;
import model.Commit;

public interface ClusteringLevel {

    void cluster(final String repo, final List<Commit> commitList, final List<String> packages, final String clusteringAlgo) throws IOException, UnknownParameterException;
}
