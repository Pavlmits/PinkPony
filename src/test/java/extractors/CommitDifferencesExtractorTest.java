package extractors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.PathFiles;
import org.junit.Ignore;
import org.junit.Test;


public class CommitDifferencesExtractorTest {

    private CommitDifferencesExtractor commitDifferencesExtractor = new CommitDifferencesExtractor();

    @Ignore
    @Test
    public void diffEntryToStringTest() {
        final List<String> expected = new ArrayList<>();
        expected.add("name.java");
        expected.add("name1.java");

        //when
        final PathFiles actual = commitDifferencesExtractor.diffEntryToString(new ArrayList<>());

        //then
        assertEquals(expected.size(), actual.getNewPaths().size());
        assertEquals(expected.get(0), actual.getNewPaths().get(0));
        assertEquals(expected.get(1), expected.get(1));
    }

    @Test
    public void diffEntryToStringEmptyListGivenTest() {
        //when
        final PathFiles actual = commitDifferencesExtractor.diffEntryToString(new ArrayList<>());

        //then
        assertTrue(actual.getNewPaths().isEmpty());
    }

}