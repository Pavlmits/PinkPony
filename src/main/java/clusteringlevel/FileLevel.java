package clusteringlevel;

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
import exception.UnknownParameterException;
import filters.FilesFilter;
import graph.GraphCreator;
import model.Commit;
import util.FileExporter;
import visualization.DotFormatGenerator;
import visualization.JavascriptToolInputGenerator;
import visualization.graphviz.GraphVizVisualizer;
import weightcalculator.CommitWeightCalculator;
import weightcalculator.WeightCalculator;

public class FileLevel implements ClusteringLevel {

    @Override
    public void cluster(final String repo, final List<Commit> commitList, final List<String> packages, final String clusteringAlgo) throws IOException, UnknownParameterException {
        final Logger logger = Logger.getLogger(FileLevel.class.getName());
        long startTime = System.nanoTime();
        final Set<String> files = new HashSet<>();
        final FilesFilter filesFilter = new FilesFilter();

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
        final WeightCalculator commitWeightCalculator = new CommitWeightCalculator();
        final GraphCreator graphCreator = new GraphCreator<String>();
        final Table weightedTable = commitWeightCalculator.calculate(files, commitList);

        logger.log(Level.INFO, "Calculate clusters...");
        Clustering clustering = ClusteringFactory.getClustering(clusteringAlgo, graphCreator);
        final Collection<Collection<String>> clusters = clustering.cluster(weightedTable);
        for (final Collection<String> cluster : clusters) {
            cluster.forEach(System.out::println);
            System.out.println("|-------------------|");

        }
        final String folder = FileExporter.generateFolderName(repo);
        FileExporter.createFolder(folder);
        FileExporter<String> fileExporter = new FileExporter<>();
        fileExporter.export(clusters, folder + "/clusters.txt");
        long endTime = System.nanoTime();
        System.out.println(clusters.size());
        final DotFormatGenerator dotFormatGenerator = new DotFormatGenerator();
        dotFormatGenerator.generate(weightedTable, folder);
        final GraphVizVisualizer graphVizVisualizer = new GraphVizVisualizer();

        graphVizVisualizer.generateGraphViz(weightedTable, folder);
        final JavascriptToolInputGenerator javascriptToolInputGenerator = new JavascriptToolInputGenerator();
        javascriptToolInputGenerator.generate(files, weightedTable, folder);
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");
    }
}
