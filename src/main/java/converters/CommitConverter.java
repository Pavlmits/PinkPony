package converters;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import extractors.CommitDifferencesExtractor;
import model.Commit;
import model.PathFiles;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.modelmapper.ModelMapper;

/**
 * Converts the RevCommit to Commit Domain
 *
 * @author pmitsou
 * @version 12/07/2019
 */
public class CommitConverter {

    private final ModelMapper modelMapper;

    private final CommitDifferencesExtractor commitDifferencesExtractor;

    @Inject
    public CommitConverter(final ModelMapper modelMapper, final CommitDifferencesExtractor commitDifferencesExtractor) {
        this.modelMapper = modelMapper;
        this.commitDifferencesExtractor = commitDifferencesExtractor;
    }

    /**
     * Converts revCommit to Domain Commit
     *
     * @param revCommit a commit type from jgit
     * @return Commit domain
     */
    public Commit convert(final RevCommit revCommit) {
        return modelMapper.map(revCommit, Commit.class);
    }

    /**
     * Converts a list of revCommits to a list of Domain commits
     *
     * @param revCommitList a list of commit type from jgit
     * @return List of Commits
     */
    public List<Commit> convertAllWithOutFiles(final List<RevCommit> revCommitList) {
        return revCommitList.stream()
                .map(revCommit -> modelMapper.map(revCommit, Commit.class))
                .collect(Collectors.toList());
    }

    /**
     * Extracts  non-merged commit with the changed files
     *
     * @param revCommit   a commit type from jgit
     * @param diffEntries a list of different entries in jgit
     * @return Commit domain
     */
    public Commit convertWithFiles(final RevCommit revCommit, final List<DiffEntry> diffEntries) {
        final Commit commit = convert(revCommit);
        commit.setMerged(revCommit.getParentCount() > 1);
        final PathFiles pathFiles = commitDifferencesExtractor.diffEntryToString(diffEntries);
        commit.setPaths(pathFiles.getNewPaths());
        commit.setOldPaths(pathFiles.getOldPaths());
        return commit;
    }

}
