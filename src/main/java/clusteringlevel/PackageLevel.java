package clusteringlevel;

import java.io.IOException;
import java.util.ArrayList;
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
import filters.ClusterFileFilter;
import filters.FilesFilter;
import graph.GraphCreator;
import model.Commit;
import model.Package;
import util.ClusterReader;
import util.FilesPrefixListChecker;
import weightcalculator.ClusterWeightCalculator;
import weightcalculator.WeightCalculator;

public class PackageLevel implements ClusteringLevel<Package> {

    @Override
    public Collection<Collection<Package>> cluster(final String repo, final List<Commit> commitList, final List<String> packages, final String clusteringAlgo) throws IOException, UnknownParameterException {
        final Logger logger = Logger.getLogger(PackageLevel.class.getName());

        final Set<String> files = new HashSet<>();
        final FilesFilter filesFilter = new FilesFilter();

        final ClusterFileFilter clusterFileFilter = new ClusterFileFilter();
        Set<Package> initialPackages = new HashSet<>();
        if (!packages.isEmpty()) {
            final List<String> clustersPaths = ClusterReader.readFromPackageName(packages.get(0), repo);
            final List<Commit> commitsToBeRemoved = new ArrayList<>();
            for (Commit commit : commitList) {
                if (FilesPrefixListChecker.isInTheList(commit.getPaths(), clustersPaths)) {
                    commit.setPaths(filesFilter.filterAll(commit.getPaths()));
                    files.addAll(commit.getPaths());
                    files.removeAll(commit.getOldPaths());
                } else {
                    commitsToBeRemoved.add(commit);
                }
            }
            commitList.removeAll(commitsToBeRemoved);
            initialPackages = clusterFileFilter.filterAndReturnClusters(files, clustersPaths);
        } else {
            //TODO for all
            for (Commit commit : commitList) {
                commit.setPaths(filesFilter.filterAll(commit.getPaths()));
                files.addAll(commit.getPaths());
                files.removeAll(commit.getOldPaths());
                //initialPackages.add(commit);
            }
        }


        logger.log(Level.INFO, "Create graph...");
        final WeightCalculator weightCalculator = new ClusterWeightCalculator();
        final GraphCreator graphCreator = new GraphCreator<Package>();
        final Table weightedTable = weightCalculator.calculate(initialPackages, commitList);
        Clustering clustering = ClusteringFactory.getClustering(clusteringAlgo, graphCreator);
        logger.log(Level.INFO, "Calculate clusters...");
        final Collection<Collection<Package>> clusters = clustering.cluster(weightedTable);
        for (final Collection<Package> aPackage : clusters) {
            aPackage.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        return clusters;
    }
}
