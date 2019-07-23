package extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import converters.CommitConverter;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;


/**
 * Extracts the commits from git history
 *
 * @author pmitsou
 * @version 19/07/2019
 */
public class CommitExtractor {

    private final Git git;
    private final CommitConverter commitConverter;
    private final CommitDifferencesExtractor commitDifferencesExtractor;

    @Inject
    public CommitExtractor(final Git git, final CommitConverter commitConverter, final CommitDifferencesExtractor commitDifferencesExtractor) {
        this.git = git;
        this.commitConverter = commitConverter;
        this.commitDifferencesExtractor = commitDifferencesExtractor;
    }

    /**
     * Extracts the commits
     *
     * @return a list of domain commits
     * @throws IOException
     * @throws GitAPIException
     */
    public List<Commit> extract(boolean hasFilesLimit) throws IOException, GitAPIException {
        final List<Commit> commitList = new ArrayList<>();
        for (RevCommit revCommit : git.log().all().call()) {
            // check for merge commits
            if (revCommit.getParentCount() > 1) {
                final List<DiffEntry> diffs = revCommit.getParents().length > 0 ? commitDifferencesExtractor.extract(git, revCommit, revCommit.getParent(0), hasFilesLimit) : new ArrayList<>();
                if (!diffs.isEmpty()) {
                    commitList.add(commitConverter.convertWithFiles(revCommit, diffs));
                }
            }
        }
        return commitList;
    }
}
