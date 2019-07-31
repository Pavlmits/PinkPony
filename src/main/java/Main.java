import java.io.IOException;
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
import filters.ClusterFilter;
import filters.FilesFilter;
import graph.GraphCreator;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jgrapht.Graph;
import org.modelmapper.ModelMapper;
import visualization.GraphVisualizer;
import weightcalculator.CommitWeightCalculator;
import weightcalculator.WeightCalculator;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, UnknownParameterException {
        long startTime = System.nanoTime();
        final Logger logger = Logger.getLogger(Main.class.getName());
        logger.log(Level.INFO, "Open repository...");
        final FilesFilter filesFilter = new FilesFilter();
        final ClusterFilter<String> clusterFilter = new ClusterFilter<>();
        final Git git = GitCreator.createLocalGitInstance(args[0]);
        final CommitConverter commitConverter = new CommitConverter(new ModelMapper(), new CommitDifferencesExtractor());
        final CommitExtractor commitExtractor = new CommitExtractor(git, commitConverter, new CommitDifferencesExtractor());
        logger.log(Level.INFO, "Extract commits...");
        final List<Commit> commitList = commitExtractor.extract(true);

        logger.log(Level.INFO, commitList.size() + " commits Extracted");
        final Set<String> files = new HashSet<>();
        logger.log(Level.INFO, "Filter commits...");
        for (Commit commit : commitList) {
            commit.setPaths(filesFilter.filterAll(commit.getPaths()));
            files.addAll(commit.getPaths());
            files.removeAll(commit.getOldPaths());
        }
        logger.log(Level.INFO, "Create graph...");
        final WeightCalculator commitWeightCalculator = new CommitWeightCalculator();
        final Table weightedTable = commitWeightCalculator.calculate(files, commitList);
        final GraphCreator graphCreator = new GraphCreator<String>();

        logger.log(Level.INFO, "Calculate clusters...");
        Clustering clustering = ClusteringFactory.getClustering(args[1], graphCreator);
        final boolean applyFilter = Boolean.parseBoolean(args[2]);
        final Collection<Collection<String>> clusters = applyFilter ? clusterFilter.filter(clustering.cluster(weightedTable)): clustering.cluster(weightedTable);
        for (final Collection<String> cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        long endTime   = System.nanoTime();
        final Graph simpleWightedGraph = graphCreator.createSimpleWightedGraph(weightedTable);
//        final GraphVisualizer<String> graphVisualizer = new GraphVisualizer<>();
//        graphVisualizer.visualize(simpleWightedGraph);
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");
    }
}
