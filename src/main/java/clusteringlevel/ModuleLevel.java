package clusteringlevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import clustering.Clustering;
import clustering.ClusteringFactory;
import com.google.common.collect.Table;
import exception.UnknownParameterException;
import filters.ClusterFileFilter;
import filters.FilesFilter;
import graph.GraphCreator;
import model.ClusteringResult;
import model.Commit;
import model.Module;
import util.FilesPrefixListChecker;
import weightcalculator.WeightCalculator;

public class ModuleLevel implements ClusteringLevel<Module> {

    private final GraphCreator graphCreator;

    private final WeightCalculator weightCalculator;

     ModuleLevel(final GraphCreator graphCreator, final WeightCalculator weightCalculator) {
        this.graphCreator = graphCreator;
        this.weightCalculator = weightCalculator;
    }

    @Override
    public ClusteringResult<Module> run(final String repo, final List<Commit> commitList, final List<String> packages, final String clusteringAlgo) throws UnknownParameterException {
        final Logger logger = Logger.getLogger(ModuleLevel.class.getName());
        final Set<String> files = new LinkedHashSet<>();
        final FilesFilter filesFilter = new FilesFilter();
        final ClusterFileFilter clusterFileFilter = new ClusterFileFilter();

        final List<Commit> commitsToBeRemoved = new ArrayList<>();
        for (Commit commit : commitList) {
            if (FilesPrefixListChecker.isInTheList(commit.getPaths(), packages)) {
                commit.setPaths(filesFilter.filterAll(commit.getPaths()));
                files.addAll(commit.getPaths());
                files.removeAll(commit.getOldPaths());
            } else {
                commitsToBeRemoved.add(commit);
            }
        }
        commitList.removeAll(commitsToBeRemoved);
        final Set<Module> initialModules = clusterFileFilter.filterAndReturnClusters(files, packages);


        logger.log(Level.INFO, "Create graph...");
        final Table weightedTable = weightCalculator.calculate(initialModules, commitList);
        Clustering clustering = ClusteringFactory.getClustering(clusteringAlgo, graphCreator);
        logger.log(Level.INFO, "Calculate clusters...");
        final Collection<Collection<Module>> clusters = clustering.cluster(weightedTable);
        for (final Collection cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        return new ClusteringResult(weightedTable, clusters, files);
    }
}
