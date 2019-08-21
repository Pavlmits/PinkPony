package extractors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
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

    @Ignore
    @Test
    public void extractAllTest() {
        final String[] args = {"one", "two", "three", "all"};
        final String repo = "repo";

        //when(moduleReader.readFromPackageName()).thenReturn();
        final List<String> actual = moduleExtractor.extract(args, repo);

        assertEquals(1, actual.size());
        assertEquals("four", actual.get(0));
    }

}