import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import clusteringlevel.ClusteringLevel;
import clusteringlevel.ClusteringLevelFactory;
import converters.CommitConverter;
import exception.UnknownParameterException;
import extractors.CommitDifferencesExtractor;
import extractors.CommitExtractor;
import git.GitCreator;
import model.Commit;
import model.Package;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;
import util.FileExporter;
import visualization.graphviz.GraphVizVisualizer;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, UnknownParameterException {
        long startTime = System.nanoTime();
        final String repo = args[0];
        final String clusteringLevel = args[1];
        final String clusteringAlgo = args[2];
        final List<String> packs;
        if (args.length > 3 && !args[3].equals("all")){
             packs = new ArrayList<>(Arrays.asList(args).subList(3, args.length));
        }else {
            packs = new ArrayList<>();
        }
        final Logger logger = Logger.getLogger(Main.class.getName());
        logger.log(Level.INFO, "Open repository...");
        final Git git = GitCreator.createLocalGitInstance(repo);
        final CommitConverter commitConverter = new CommitConverter(new ModelMapper(), new CommitDifferencesExtractor());
        final CommitExtractor commitExtractor = new CommitExtractor(git, commitConverter, new CommitDifferencesExtractor());
        logger.log(Level.INFO, "Extract commits...");
        final List<Commit> commitList = commitExtractor.extract(true);
        logger.log(Level.INFO, commitList.size() + " commits Extracted");
        logger.log(Level.INFO, "Filter commits...");
        final ClusteringLevel clustering = ClusteringLevelFactory.getClusteringLevel(clusteringLevel);
        clustering.cluster(repo, commitList, packs, clusteringAlgo);

        final String folder = FileExporter.generateFolderName(repo);
        FileExporter<Package> fileExporter = new FileExporter<>();
        FileExporter.createFolder(folder);
        fileExporter.export(clusters, folder + "/clusterClusters.txt");
        long endTime = System.nanoTime();
        System.out.println(clusters.size());


        final GraphVizVisualizer<Package> graphVizVisualizer = new GraphVizVisualizer();
        graphVizVisualizer.generate(weightedTable, folder);
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");

    }
}
