package converters;

import java.util.List;
import java.util.stream.Collectors;

import extractors.CommitDifferencesExtractor;
import model.Commit;
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

    public CommitConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts revCommit to Domain Commit
     * @param revCommit
     * @return Commit
     */
    public Commit convert(final RevCommit revCommit) {
        return modelMapper.map(revCommit, Commit.class);
    }

    /**
     * Converts a list of revCommits to a list of Domain commits
     * @param revCommitList
     * @return List of Commits
     */
    public List<Commit> convertAllWithOutFiles(final List<RevCommit> revCommitList) {
        return revCommitList.stream()
                .map(revCommit -> modelMapper.map(revCommit, Commit.class))
                .collect(Collectors.toList());
    }

    /**
     * Extracts commited files
     * @param revCommit
     * @param diffEntries
     * @return Commit domain
     */
    public Commit convertWithFiles(final RevCommit revCommit, final List<DiffEntry> diffEntries){
        final Commit commit = convert(revCommit);
        commit.setPaths(CommitDifferencesExtractor.diffEntryToString(diffEntries));
        return commit;
    }

}
