import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, UnknownParameterException {
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
    }
}
