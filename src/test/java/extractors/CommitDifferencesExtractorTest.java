package extractors;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import converters.CommitConverter;
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
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CommitDifferencesExtractorTest {

    private CommitDifferencesExtractor commitDifferencesExtractor = new CommitDifferencesExtractor();



    @Ignore
    @Test
    @PrepareForTest(CommitDifferencesExtractor.class)
    public void diffEntryToStringTest() {
        final List<String> expected = new ArrayList<>();
        expected.add("name.java");
        expected.add("name1.java");

        //when
        final List<String> actual = commitDifferencesExtractor.diffEntryToString(new ArrayList<>());

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

}