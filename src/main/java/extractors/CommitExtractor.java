package extractors;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class CommitExtractor {

    private final Git git;

    public CommitExtractor(final String path) throws IOException {
        this.git = new Git(openJGitRepository(path));
    }


    public List<RevCommit> extract() throws IOException, GitAPIException {
        final Iterable<RevCommit> commits = git.log().all().call();
        final List<RevCommit> commitsList = new LinkedList<>();
        commits.forEach(commitsList::add);
        return commitsList;
    }

    public Map<RevCommit, List<DiffEntry>> extractFiles(final List<RevCommit> commitList) throws IOException, GitAPIException {
        final Map<RevCommit, List<DiffEntry>> commitMap = new HashMap<>();
        for (int i = 0; i < commitList.size() - 1; i++) {

            List<DiffEntry> diffs = CommitFilesExtractor.extract(git, commitList.get(i), commitList.get(i).getParent(0));
            commitMap.put(commitList.get(i), diffs);
        }
        return commitMap;
    }

    private Repository openJGitRepository(String path) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(path))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

}

