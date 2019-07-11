package extractors;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class CommitFilesExtractor {

    public List<DiffEntry> extract(final Git git, final RevCommit oldCommit, final RevCommit newCommit) throws IOException, GitAPIException {
        return git.diff()
                .setOldTree(prepareTreeParser(git.getRepository(), oldCommit))
                .setNewTree(prepareTreeParser(git.getRepository(), newCommit))
                .call();
    }

    private  AbstractTreeIterator prepareTreeParser(final Repository repository, final RevCommit commit) throws IOException {
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
//
//    public static List<DiffEntry> diffsForTheCommit(Repository repo, RevCommit commit) throws IOException {
//
//        AnyObjectId currentCommit = repo.resolve(commit.getName());
//        AnyObjectId parentCommit = commit.getParentCount() > 0 ? repo.resolve(commit.getParent(0).getName()) : null;
//        System.out.println("ok");
//        return getDiffBetweenCommits(repo, parentCommit, currentCommit);
//    }
//
//    public static List<DiffEntry> getDiffBetweenCommits(Repository repo, AnyObjectId parentCommit,
//                                                  AnyObjectId currentCommit) {
//        try (DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE)) {
//
//            df.setBinaryFileThreshold(2 * 1024); // 2 mb max a file
//            df.setRepository(repo);
//            df.setDiffComparator(RawTextComparator.DEFAULT);
//            df.setDetectRenames(true);
//
//            setContext(df);
//
//            List<DiffEntry> diffs = null;
//            if (parentCommit == null) {
//                try (RevWalk rw = new RevWalk(repo)) {
//                    RevCommit commit = rw.parseCommit(currentCommit);
//                    diffs = df.scan(new EmptyTreeIterator(),
//                            new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
//                }
//            } else {
//                diffs = df.scan(parentCommit, currentCommit);
//            }
//            return diffs;
//        } catch (IOException e) {
//            throw new RuntimeException(
//                    "error diffing " + parentCommit.getName() + " and " + currentCommit.getName() + " in " , e);
//        }
//    }
//
//    private static void setContext(DiffFormatter df) {
//        try {
//            int context = getSystemProperty("git.diffcontext"); /* TODO: make it into a configuration */
//            df.setContext(context);
//        } catch (Exception e) {
//            return;
//        }
//    }
//
//    private static int getSystemProperty(String name) throws NumberFormatException {
//        String val = System.getProperty(name);
//        return Integer.parseInt(val);
//    }
}
