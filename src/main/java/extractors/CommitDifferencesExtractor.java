package extractors;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Extracts differences from a commit
 *
 * @author pmitsou
 * @version 12/07/2019
 */
public class CommitDifferencesExtractor {

    private static final int MAX_NUMBER_OF_FILES_IN_COMMIT = 15;

    @Inject
    public CommitDifferencesExtractor() {
    }

    /**
     * Converts the Files at DiffEntry to String
     *
     * @param filesList
     * @return list of String
     */
    public List<String> diffEntryToString(final List<DiffEntry> filesList) {
        final List<String> files = new ArrayList<>();
        for (DiffEntry entry : filesList) {
            files.add(entry.getNewPath());
        }
        return files;
    }

    /**
     * Extracts the commits from all branches
     *
     * @param git the git instance of jgit
     * @param oldCommit
     * @param newCommit
     * @return a list of the differences between  the 2 commits
     * @throws IOException
     */
    public List<DiffEntry> extract(final Git git, final RevCommit oldCommit, final RevCommit newCommit) throws IOException {
        FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
        try (DiffFormatter diffFormatter = new DiffFormatter(stdout)) {
            diffFormatter.setRepository(git.getRepository());
            final List<DiffEntry> diffEntriesList = diffFormatter.scan(oldCommit, newCommit);
            return diffEntriesList.size() > MAX_NUMBER_OF_FILES_IN_COMMIT ? new ArrayList<>() : diffEntriesList;
            //return diffEntriesList;

        }
    }

}
