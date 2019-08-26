package visualization;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Ignore;
import org.junit.Test;

public class JavascriptToolInputGeneratorTest {

    private JavascriptToolInputGenerator<String> javascriptToolInputGenerator = new JavascriptToolInputGenerator<>();

    @Ignore
    @Test
    public void generateEmptyTable() throws FileNotFoundException, UnsupportedEncodingException {
        final Table<String, String, Integer> table = HashBasedTable.create();
        javascriptToolInputGenerator.generate(table, "");

    }

}