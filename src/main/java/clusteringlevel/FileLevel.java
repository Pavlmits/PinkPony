package clusteringlevel;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import clustering.Clustering;
import clustering.ClusteringFactory;
import com.google.common.collect.Table;
import exception.UnknownParameterException;
import filters.FilesFilter;
import graph.GraphCreator;
import model.ClusteringResult;
import model.Commit;
import weightcalculator.WeightCalculator;

public class FileLevel implements ClusteringLevel<String> {

    private final FilesFilter filesFilter;

    private final GraphCreator graphCreator;

    private final WeightCalculator weightCalculator;

    public FileLevel(final FilesFilter filesFilter, final GraphCreator graphCreator, final WeightCalculator weightCalculator) {
        this.filesFilter = filesFilter;
        this.graphCreator = graphCreator;
        this.weightCalculator = weightCalculator;
    }


    @Override
    public ClusteringResult<String> run(final String repo, final List<Commit> commitList, final List<String> packages, final String clusteringAlgo) throws UnknownParameterException {
        final Logger logger = Logger.getLogger(FileLevel.class.getName());
        final Set<String> files = new HashSet<>();

        for (Commit commit : commitList) {
            commit.setPaths(filesFilter.filterAll(commit.getPaths()));
            if (!packages.isEmpty()) {
                files.addAll(filesFilter.filterBasedOnPackage(commit.getPaths(), packages));
            } else {
                files.addAll(commit.getPaths());
            }
            files.removeAll(commit.getOldPaths());
        }

        logger.log(Level.INFO, "Create graph...");

        final Table weightedTable = weightCalculator.calculate(files, commitList);

        logger.log(Level.INFO, "Calculate clusters...");
        Clustering clustering = ClusteringFactory.getClustering(clusteringAlgo, graphCreator);
        final Collection<Collection<String>> clusters = clustering.cluster(weightedTable);
        for (final Collection<String> cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }

        return new ClusteringResult<>(weightedTable, clusters, files);
    }
}
