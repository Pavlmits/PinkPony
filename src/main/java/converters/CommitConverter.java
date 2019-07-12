package converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<Commit> convertAll(final List<RevCommit> revCommitList) {
        return revCommitList.stream()
                .map(revCommit -> modelMapper.map(revCommit, Commit.class))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param commitDiffEntryMap
     * @return
     */
    public List<Commit> convertAllWithFiles(final Map<RevCommit, List<DiffEntry>> commitDiffEntryMap) {
        final List<Commit> commitList = new ArrayList<>();
        for (Map.Entry<RevCommit, List<DiffEntry>> entry : commitDiffEntryMap.entrySet()) {
            final Commit commit = convert(entry.getKey());
            commit.setPaths(CommitDifferencesExtractor.diffEntryToString(entry.getValue()));
            commitList.add(commit);
        }
        return commitList;
    }


}
