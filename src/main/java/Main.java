import java.io.IOException;
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
import extractors.ModuleExtractor;
import git.GitCreator;
import model.ClusteringResult;
import model.Commit;
import model.Module;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;
import util.FileHandler;
import util.ModuleReader;
import visualization.JavascriptToolInputGenerator;
import visualization.graphviz.GraphVizVisualizer;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, UnknownParameterException {
        long startTime = System.nanoTime();
        //parse arguments
        final String repo = args[0];
        final String clusteringLevel = args[1];
        final String clusteringAlgo = args[2];
        final ModuleExtractor moduleExtractor = new ModuleExtractor(new ModuleReader());
        final List<String> packs = moduleExtractor.extract(args, repo);

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
        final ClusteringResult clusteringResult = clustering.run(repo, commitList, packs, clusteringAlgo);

        final String folder = FileHandler.generateFolderName(repo);
        FileHandler<Module> fileExporter = new FileHandler<>();
        FileHandler.createFolder(folder);
        fileExporter.export(clusteringResult.getClusters(), folder + "/clusterClusters.txt");
        long endTime = System.nanoTime();
        System.out.println(clusteringResult.getClusters().size());

        final JavascriptToolInputGenerator javascriptToolInputGenerator = new JavascriptToolInputGenerator();
        javascriptToolInputGenerator.generate(clusteringResult.getWeightTable(), folder);
        final GraphVizVisualizer<Module> graphVizVisualizer = new GraphVizVisualizer();
        graphVizVisualizer.generate(clusteringResult.getWeightTable(), folder + "/visualization/graphVizFile.dot");
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println(totalTime + " seconds");

    }
}
