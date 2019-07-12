import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import converters.CommitConverter;
import extractors.CommitDifferencesExtractor;
import extractors.CommitExtractor;
import extractors.RevCommitExtractor;
import filters.FilesFilter;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {

        final String path = "C:\\dev\\algorithms-wfa\\.git";
        final String pa = "C:\\Users\\pmitsou\\Documents\\repodriller\\.git";
        final FilesFilter filesFilter =  new FilesFilter();
        final Git git = GitCreator.createLocalGitInstance(path);
        final CommitExtractor commitExtractor = new CommitExtractor(git, new RevCommitExtractor(new CommitDifferencesExtractor()), new CommitConverter(new ModelMapper()));
        final List<Commit> commitList = commitExtractor.extract();
        final Set<String> files = new HashSet<>();
        for (Commit commit: commitList) {
            commit.setPaths(filesFilter.filterAll(commit.getPaths()));
            files.addAll(commit.getPaths());
        }
        System.out.println(files.size());
        for (final String file : files) {
            System.out.println(file);
        }

        final GraphCreator graphCreator = new GraphCreator();
        final Table<String, String, Integer> fileTable = graphCreator.weightCalculator(files, commitList);

        for (Table.Cell<String, String, Integer> cell : fileTable.cellSet()) {
            System.out.println(cell.getRowKey()
                    + "    |      "
                    + cell.getColumnKey()
                    + "|"
                    + cell.getValue());
        }


    }
}
