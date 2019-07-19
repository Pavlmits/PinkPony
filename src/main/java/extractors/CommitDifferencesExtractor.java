package extractors;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

/**
 * Extracts differences from a commit
 *
 * @author pmitsou
 * @version 12/07/2019
 */
public class CommitDifferencesExtractor {

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
     * Extracts the commits from all branch
     * @param git
     * @param oldCommit
     * @param newCommit
     * @return
     * @throws IOException
     */
    public List<DiffEntry> quickExtract(final Git git, final RevCommit oldCommit, final RevCommit newCommit) throws IOException {
        FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
        try (DiffFormatter diffFormatter = new DiffFormatter(stdout)) {
            diffFormatter.setRepository(git.getRepository());
            return diffFormatter.scan(oldCommit, newCommit);

        }
    }

    /**
     * Extract the changed files in a commit
     *
     * @param git
     * @param oldCommit
     * @param newCommit
     * @return
     * @throws GitAPIException
     */
    public List<DiffEntry> extract(final Git git, final RevCommit oldCommit, final RevCommit newCommit) throws GitAPIException {
        try {
            return git.diff()
                    .setOldTree(prepareTreeParser(git.getRepository(), oldCommit))
                    .setNewTree(prepareTreeParser(git.getRepository(), newCommit))
                    .call();
        } catch (GitAPIException e) {
            System.err.println("CommitDifferencesExtractor : Could not call the Git API");
            throw e;
        }

    }

    private AbstractTreeIterator prepareTreeParser(final Repository repository, final RevCommit commit) {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        final CanonicalTreeParser treeParser = new CanonicalTreeParser();
        try (RevWalk walk = new RevWalk(repository)) {
            final RevTree tree = walk.parseTree(commit.getTree().getId());
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();
        } catch (IOException e) {
            System.err.println("Could not read from the tree!");
        }
        return treeParser;

    }

}
