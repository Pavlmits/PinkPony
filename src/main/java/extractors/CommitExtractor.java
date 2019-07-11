package extractors;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class CommitExtractor {

   private final CommitFilesExtractor commitFilesExtractor;

    public CommitExtractor(final CommitFilesExtractor commitFilesExtractor) {
        this.commitFilesExtractor = commitFilesExtractor;
    }

    public  List<RevCommit> extractWithOutFiles(final Git git) throws IOException, GitAPIException {
        final Iterable<RevCommit> commits = git.log().all().call();
        final List<RevCommit> commitsList = new LinkedList<>();
        commits.forEach(commitsList::add);
        return commitsList;
    }

    public  Map<RevCommit, List<DiffEntry>> extractWithFiles(final Git git) throws IOException, GitAPIException {
        final List<RevCommit> commitList = extractWithOutFiles(git);
        final Map<RevCommit, List<DiffEntry>> commitMap = new HashMap<>();
        for (int i = 0; i < commitList.size() - 1; i++) {
            List<DiffEntry> diffs = commitFilesExtractor.extract(git, commitList.get(i), commitList.get(i).getParent(0));
            commitMap.put(commitList.get(i), diffs);
        }
        return commitMap;
    }


    //TODO add converter from RevCommit to Commit domain
}

