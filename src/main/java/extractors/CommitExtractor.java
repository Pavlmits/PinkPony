package extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import converters.CommitConverter;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class CommitExtractor {

    private final Git git;
    private final RevCommitExtractor revCommitExtractor;
    private final CommitConverter commitConverter;
    private final CommitDifferencesExtractor commitDifferencesExtractor;

    public CommitExtractor(final Git git, final RevCommitExtractor revCommitExtractor, final CommitConverter commitConverter, final CommitDifferencesExtractor commitDifferencesExtractor) {
        this.git = git;
        this.revCommitExtractor = revCommitExtractor;
        this.commitConverter = commitConverter;
        this.commitDifferencesExtractor = commitDifferencesExtractor;
    }

    public List<Commit> extract() throws IOException, GitAPIException {
        final List<Commit> commitList = new ArrayList<>();
        for (RevCommit revCommit : git.log().all().call()) {
            final List<DiffEntry> diffs = revCommit.getParents().length > 0 ? commitDifferencesExtractor.extract(git, revCommit, revCommit.getParent(0)) : new ArrayList<>();
            commitList.add(commitConverter.convertWithFiles(revCommit, diffs));
        }
        return commitList;
    }
}
