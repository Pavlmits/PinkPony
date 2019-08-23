package extractors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import util.ModuleReader;

@RunWith(MockitoJUnitRunner.class)
public class ModuleExtractorTest {

    @InjectMocks
    private ModuleExtractor moduleExtractor;

    @Mock
    private ModuleReader moduleReader;

    @Test
    public void extractTest() {
        final String[] args = {"one", "two", "three", "all"};
        final String repo = "repo";
        final List<String> expected = new ArrayList<>();
        expected.add("pa");

        when(moduleReader.readFromPackageName("all", repo)).thenReturn(expected);
        final List<String> actual = moduleExtractor.extract(args, repo);

        assertEquals(1, actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }

}