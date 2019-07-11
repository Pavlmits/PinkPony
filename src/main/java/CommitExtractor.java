import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class CommitExtractor {

    private final String path;

    private final Git git;

    public CommitExtractor(final String path) throws IOException {
        this.path = path;
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
        for (int i=0;i< commitList.size()-1; i++) {

            List<DiffEntry> diffs = extractDiffs(git, commitList.get(i), commitList.get(i).getParent(0));
//            commitMap.put(commitList.get(i), diffs);
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

    private List<DiffEntry> extractDiffs(final Git git, final RevCommit oldCommit, final RevCommit newCommit) throws IOException, GitAPIException {
        long startTime = System.nanoTime();
        final List<DiffEntry> call = git.diff()
                .setOldTree(prepareTreeParser(git.getRepository(), oldCommit))
                .setNewTree(prepareTreeParser(git.getRepository(), newCommit))
                .call();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(duration /1000) +" seconds");
        return call;
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, RevCommit commit) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates

        try (RevWalk walk = new RevWalk(repository)) {

            final RevTree tree = walk.parseTree(commit.getTree().getId());

            final CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();
            return treeParser;
        }
    }

}

