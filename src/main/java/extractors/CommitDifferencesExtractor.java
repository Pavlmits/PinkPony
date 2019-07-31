package extractors;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.PathFiles;
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

    private static final int MAX_NUMBER_OF_FILES_IN_COMMIT = 30;

    /**
     * Converts the Files at DiffEntry to String
     *
     * @param filesList
     * @return list of String
     */
    public PathFiles diffEntryToString(final List<DiffEntry> filesList) {
        final List<String> newPaths = new ArrayList<>();
        final List<String> oldPaths = new ArrayList<>();

        for (DiffEntry entry : filesList) {
            newPaths.add(entry.getNewPath());
            if (!entry.getNewPath().equals(entry.getOldPath())) {
                oldPaths.add(entry.getOldPath());
            }
        }
        return new PathFiles(newPaths, oldPaths);
    }

    /**
     * Extracts the commits from all branches
     *
     * @param git       the git instance of jgit
     * @param oldCommit
     * @param newCommit
     * @return a list of the differences between  the 2 commits
     * @throws IOException
     */
    public List<DiffEntry> extract(final Git git, final RevCommit oldCommit, final RevCommit newCommit, boolean filesLimit) throws IOException {
        FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
        try (DiffFormatter diffFormatter = new DiffFormatter(stdout)) {
            diffFormatter.setRepository(git.getRepository());
            final List<DiffEntry> diffEntriesList = diffFormatter.scan(oldCommit, newCommit);
            if (filesLimit) {
                return diffEntriesList.size() > MAX_NUMBER_OF_FILES_IN_COMMIT ? new ArrayList<>() : diffEntriesList;
            } else {
                return diffEntriesList;
            }

        }
    }

}
