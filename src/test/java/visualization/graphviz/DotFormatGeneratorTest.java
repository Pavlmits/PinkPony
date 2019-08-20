package visualization.graphviz;

import static org.junit.Assert.*;

import java.io.IOException;

import com.google.common.collect.Table;
import org.junit.Ignore;
import org.junit.Test;

public class DotFormatGeneratorTest {

    @Ignore
    @Test
    public void dotToTableTest() throws IOException {
        final DotFormatGenerator dotFormatGenerator = new DotFormatGenerator();

        final Table<String, String, Integer> table = dotFormatGenerator.dotToTableGenerator("graphVizFile.dot");

        assertTrue(!table.isEmpty());
    }
}