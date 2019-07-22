package converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import extractors.CommitDifferencesExtractor;
import model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class CommitConverterTest {


    @InjectMocks
    private CommitConverter commitConverter;

    @Mock
    private ModelMapper modelMapper;

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
    public void convertTest(){
        final Commit expected = new Commit();
        when(modelMapper.map(revCommitSubList.get(0), Commit.class)).thenReturn(expected);

        //when
        final Commit actual = commitConverter.convert(revCommitSubList.get(0));

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void convertAllWithOutFilesTest(){
        final Commit expected = new Commit();
        for (final RevCommit revCommit : revCommitSubList) {
            when(modelMapper.map(revCommit, Commit.class)).thenReturn(expected);
        }
        //when
        final List<Commit> commits = commitConverter.convertAllWithOutFiles(revCommitSubList);
        //then
        assertEquals(expected, commits.get(0));
        assertEquals(expected, commits.get(1));
        assertEquals(2, commits.size());

    }


    @Ignore
    @Test
    public void convertWithFilesTest(){
        final List<DiffEntry> emptyDiffEntries = new ArrayList<>();
        final List<String> paths = new ArrayList<>();
        paths.add("ok");
        when(commitDifferencesExtractor.diffEntryToString(emptyDiffEntries)).thenReturn(paths);

        final Commit actual = commitConverter.convertWithFiles(revCommitSubList.get(0), emptyDiffEntries);

        assertEquals(actual.getPaths().get(0), "ok");
    }

}