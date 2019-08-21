package util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class ModuleReaderTest {

    @Test(expected = IOException.class)
    public void whenFileNotExistThenThrowException() throws IOException {
        ModuleReader.readFromFile("lalalala");
    }

    @Test
    public void readTest() throws IOException {
        final String file = "src\\test\\resources\\util\\file.txt";
        final List<String> content = ModuleReader.readFromFile(file);
        assertEquals(1, content.size());
        assertEquals("lalala", content.get(0));
    }


}