import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import clustering.Clustering;
import clustering.ClusteringFactory;
import com.google.common.collect.Table;
import converters.CommitConverter;
import exception.UnknownParameterException;
import extractors.CommitDifferencesExtractor;
import extractors.CommitExtractor;
import filters.ClusterFileFilter;
import filters.FilesFilter;
import git.GitCreator;
import graph.GraphCreator;
import model.Cluster;
import model.Commit;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;
import util.ClusterFileReader;
import util.FileExporter;
import util.FilesPrefixListChecker;
import weightcalculator.ClusterWeightCalculator;
import weightcalculator.WeightCalculator;

public class ClusterClusters {

    public static void main(String[] args) throws IOException, GitAPIException, UnknownParameterException {
        long startTime = System.nanoTime();
        //parse args
        final String repo = args[0];
        final String clusteringMethod = args[1];
        final String clusterFile = args[2];

        final List<String> clustersPaths = ClusterFileReader.read(clusterFile);
        final Logger logger = Logger.getLogger(Main.class.getName());
        logger.log(Level.INFO, "Open repository...");
        final Git git = GitCreator.createLocalGitInstance(repo);
        final FilesFilter filesFilter = new FilesFilter();
        final ClusterFileFilter clusterFileFilter = new ClusterFileFilter();
        final CommitConverter commitConverter = new CommitConverter(new ModelMapper(), new CommitDifferencesExtractor());
        final CommitExtractor commitExtractor = new CommitExtractor(git, commitConverter, new CommitDifferencesExtractor());
        logger.log(Level.INFO, "Extract commits...");
        final List<Commit> commitList = commitExtractor.extract(true);
        logger.log(Level.INFO, commitList.size() + " commits Extracted");
        final Set<String> files = new HashSet<>();
        logger.log(Level.INFO, "Filter commits...");
        final List<Commit> commitsToBeRemoved = new ArrayList<>();
        for (Commit commit : commitList) {
            if (FilesPrefixListChecker.isInTheList(commit.getPaths(),clustersPaths)) {
                commit.setPaths(filesFilter.filterAll(commit.getPaths()));
                files.addAll(commit.getPaths());
                files.removeAll(commit.getOldPaths());
            }else{
                commitsToBeRemoved.add(commit);
            }
        }
        commitList.removeAll(commitsToBeRemoved);
        final Set<Cluster> initialClusters = clusterFileFilter.filterAndReturnClusters(files, clustersPaths);

        logger.log(Level.INFO, "Create graph...");
        final WeightCalculator weightCalculator = new ClusterWeightCalculator();
        final GraphCreator graphCreator = new GraphCreator<Cluster>();
        final Table weightedTable = weightCalculator.calculate(initialClusters, commitList);
        Clustering clustering = ClusteringFactory.getClustering(clusteringMethod, graphCreator);
        final Collection<Collection<Cluster>> clusters = clustering.cluster(weightedTable);
        for (final Collection<Cluster> cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        FileExporter.exportClusters(clusters, "clusterClusters.txt");
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");

    }
}
