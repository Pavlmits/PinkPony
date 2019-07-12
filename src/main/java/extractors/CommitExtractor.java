package extractors;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public CommitExtractor(final Git git, final RevCommitExtractor revCommitExtractor, final CommitConverter commitConverter) {
        this.git = git;
        this.revCommitExtractor = revCommitExtractor;
        this.commitConverter = commitConverter;
    }

    public List<Commit> extract() throws IOException, GitAPIException {
        final Map<RevCommit, List<DiffEntry>> commitMap = revCommitExtractor.extractWithFiles(git);
        return commitConverter.convertAllWithFiles(commitMap);
    }
}
