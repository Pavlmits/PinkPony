package extractors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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