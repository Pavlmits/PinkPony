package extractors;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import converters.CommitConverter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommitExtractorTest {

    @InjectMocks
    private CommitExtractor commitExtractor;

    @Mock
    private CommitConverter commitConverter;

    @Mock
    private CommitDifferencesExtractor commitDifferencesExtractor;

    private Git git;

    private List<RevCommit> revCommitSubList;

    @Before
    public void init() throws IOException, GitAPIException {
        git = new Git(new FileRepositoryBuilder()
                .setGitDir(new File(".git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build());
        final Iterable<RevCommit> revCommits = git.log().all().call();
        final List<RevCommit> revCommitsList = Lists.newArrayList(revCommits);
        revCommitSubList = revCommitsList.subList(0, 2);
    }

    @Test
    public void extractTest(){

    }



}