package extractors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.PathFiles;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.junit.Test;


public class CommitDifferencesExtractorTest {

    private CommitDifferencesExtractor commitDifferencesExtractor = new CommitDifferencesExtractor();

    @Test
    public void diffEntryToStringTest() {
        final List<String> expected = new ArrayList<>();
        expected.add("New path");

        List<DiffEntry> diffEntries = new ArrayList<>();
        diffEntries.add(new DiffEntryStub());

        //when
        final PathFiles actual = commitDifferencesExtractor.diffEntryToString(diffEntries);

        //then
        assertEquals(expected.size(), actual.getNewPaths().size());
        assertEquals(expected.get(0), actual.getNewPaths().get(0));
    }

    @Test
    public void diffEntryToStringEmptyListGivenTest() {
        //when
        final PathFiles actual = commitDifferencesExtractor.diffEntryToString(new ArrayList<>());

        //then
        assertTrue(actual.getNewPaths().isEmpty());
    }



}