package converters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class CommitConverterTest {

    @Mock
    private ModelMapper modelMapper;

    private Git git;

    @InjectMocks
    private CommitConverter commitConverter = new CommitConverter(modelMapper);


    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        git = new Git(new FileRepositoryBuilder()
                .setGitDir(new File("\\.git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build());
    }


    @Test
    public void convertTest() throws GitAPIException, IOException {
        final Commit commit = new Commit();
        final List<RevCommit> revCommitList = (List<RevCommit>) git.log().all().call();
        when(modelMapper.map(revCommitList.get(0), Commit.class)).thenReturn(commit);

        assertEquals(commit, commitConverter.convert(revCommitList.get(0)));
    }


}