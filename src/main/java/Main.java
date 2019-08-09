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
import filters.ClusterFilter;
import filters.FilesFilter;
import git.GitCreator;
import graph.GraphCreator;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;
import util.FileExporter;
import visualization.ClassDiagramGenerator;
import visualization.DotFormatGenerator;
import visualization.JavascriptToolInputGenerator;
import visualization.graphviz.GraphVizVisualizer;
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
            if(args.length >= 4 ){
                files.addAll(filesFilter.filterBasedOnPackage(commit.getPaths(), args[3]));
            }else{
                files.addAll(commit.getPaths());
            }
            files.removeAll(commit.getOldPaths());
        }
        logger.log(Level.INFO, "Create graph...");
        final WeightCalculator commitWeightCalculator = new CommitWeightCalculator();
        final Table weightedTable = commitWeightCalculator.calculate(files, commitList);
        final GraphCreator graphCreator = new GraphCreator<String>();

        logger.log(Level.INFO, "Calculate clusters...");
        Clustering clustering = ClusteringFactory.getClustering(args[1], graphCreator);
        final boolean applyFilter = Boolean.parseBoolean(args[2]);
        final Collection<Collection<String>> clusters = applyFilter ? clusterFilter.filter(clustering.cluster(weightedTable)) : clustering.cluster(weightedTable);
        for (final Collection<String> cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        FileExporter.export(clusters, "clusters.txt");
        long endTime = System.nanoTime();
        final List<Collection<String>> clusterList = new ArrayList<>(clusters);
        System.out.println(clusterList.size());
        ClassDiagramGenerator.generate(clusterList);
        final DotFormatGenerator dotFormatGenerator = new DotFormatGenerator();
        //dotFormatGenerator.subgraph(clusterList, weightedTable);
        dotFormatGenerator.generate(weightedTable);
        final GraphVizVisualizer graphVizVisualizer = new GraphVizVisualizer(dotFormatGenerator);
        //graphVizVisualizer.generateGraphViz(weightedTable);
        final JavascriptToolInputGenerator javascriptToolInputGenerator = new JavascriptToolInputGenerator();
        javascriptToolInputGenerator.generate(files, weightedTable);
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");
    }
}
