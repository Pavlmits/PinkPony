package extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Extract the RevCommits from git
 *
 * @author pmitsou
 * @version 12/07/2019
 */
public class RevCommitExtractor {

    private final CommitDifferencesExtractor commitDifferencesExtractor;

    public RevCommitExtractor(final CommitDifferencesExtractor commitDifferencesExtractor) {
        this.commitDifferencesExtractor = commitDifferencesExtractor;
    }

    public List<RevCommit> extractWithOutFiles(final Git git) throws IOException, GitAPIException {
        final List<RevCommit> commitsList = new ArrayList<>();

        final Iterable<RevCommit> commits = git.log().all().call();
        commits.forEach(commitsList::add);

        return commitsList;
    }

    public Map<RevCommit, List<DiffEntry>> extractWithFiles(final Git git) throws GitAPIException, IOException {
        final List<RevCommit> commitList = extractWithOutFiles(git);
        final Map<RevCommit, List<DiffEntry>> commitMap = new HashMap<>();
        //TODO change size of loop
        for (int i = 0; i < commitList.size(); i++) {
            List<DiffEntry> diffs = commitList.get(i).getParents().length > 0 ? commitDifferencesExtractor.extract(git, commitList.get(i), commitList.get(i).getParent(0)) : new ArrayList<>();
            commitMap.put(commitList.get(i), diffs);
        }
        return commitMap;
    }

}

