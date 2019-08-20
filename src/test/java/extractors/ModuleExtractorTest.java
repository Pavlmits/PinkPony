package extractors;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class ModuleExtractorTest {

    @Ignore("I will do it")
    @Test
    public void extractTest(){
        String[] args = {"one", "two", "three", "four"};
        String repo = "repo";

        List<String> actual = ModuleExtractor.extract(args, repo);

        assertEquals(1,actual.size());
        assertEquals("four", actual.get(0));
    }

}