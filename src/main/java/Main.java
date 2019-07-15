import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import converters.CommitConverter;
import extractors.CommitDifferencesExtractor;
import extractors.CommitExtractor;
import extractors.RevCommitExtractor;
import filters.FilesFilter;
import graph.GraphCreator;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.modelmapper.ModelMapper;
import weightCalculator.CommitWeightCalculator;
import weightCalculator.WeightCalculator;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {

        final String path = "C:\\dev\\algorithms-wfa\\.git";
        final String pa = "C:\\Users\\pmitsou\\Documents\\repodriller\\.git";
        final FilesFilter filesFilter =  new FilesFilter();
        final Git git = GitCreator.createLocalGitInstance(pa);
        final CommitExtractor commitExtractor = new CommitExtractor(git, new RevCommitExtractor(new CommitDifferencesExtractor()), new CommitConverter(new ModelMapper()), new CommitDifferencesExtractor());
        final List<Commit> commitList = commitExtractor.extract();
        final Set<String> files = new HashSet<>();
        for (Commit commit: commitList) {
            commit.setPaths(filesFilter.filterAll(commit.getPaths()));
            files.addAll(commit.getPaths());
        }
        final WeightCalculator commitWeightCalculator = new CommitWeightCalculator();
        final GraphCreator graphCreator = new GraphCreator<Commit, String>(commitWeightCalculator);
        final Table<String, String, Integer> fileTable = commitWeightCalculator.calculate(files, commitList);
        final Graph<String, DefaultWeightedEdge> graph = graphCreator.createSimpleWightedGraph(files, commitList);
        final MutableValueGraph<String, Integer> weightedGraph = graphCreator.create(files, commitList);
        System.out.println("ok");
        for (Table.Cell<String, String, Integer> cell : fileTable.cellSet()) {
            System.out.println(cell.getRowKey()
                    + "    |      "
                    + cell.getColumnKey()
                    + "|"
                    + cell.getValue());
        }


    }
}
