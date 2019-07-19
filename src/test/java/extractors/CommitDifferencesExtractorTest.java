package extractors;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.junit.Test;

public class CommitDifferencesExtractorTest {

    private CommitDifferencesExtractor commitDifferencesExtractor = new CommitDifferencesExtractor();

    private Git git;

    private List<DiffEntry> subDiffList;

    private List<RevCommit> revCommitSubList;

    @Before
    public void init() throws IOException, GitAPIException {
        git = new Git(new FileRepositoryBuilder()
                .setGitDir(new File(".git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build());
        final Repository repository = git.getRepository();
        final Iterable<RevCommit> revCommits = git.log().all().call();
        final List<RevCommit> revCommitsList = Lists.newArrayList(revCommits);
        revCommitSubList = revCommitsList.subList(0, 2);
//        final TreeWalk treeWalk = new TreeWalk(repository);
//        final List<DiffEntry> diffEntry = DiffEntry.scan(treeWalk, false);
//        subDiffList = diffEntry.subList(0,2);
    }

    @Test
    public void diffEntryToStringTest() {
        final List<String> expected = new ArrayList<>();
        expected.add("name.java");
        expected.add("name1.java");

        //when
        final List<String> actual = commitDifferencesExtractor.diffEntryToString(subDiffList);

        //then
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), expected.get(1));
    }

    @Test
    public void diffEntryToStringEmptyListGivenTest() {
        //when
        final List<String> actual = commitDifferencesExtractor.diffEntryToString(new ArrayList<>());

        //then
        assertTrue(actual.isEmpty());
    }

    @Test(expected = GitAPIException.class)
    public void whenExtractFromNullCommitsExpectExceptionTest() throws GitAPIException {
        //when
        commitDifferencesExtractor.extract(git, RevCommit.parse(new byte[0]), RevCommit.parse(new byte[0]));
    }

    @Test
    public void prepareTestTree() {


    }
}