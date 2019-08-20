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

    @Ignore
    @Test
    public void readTest() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("util/file.txt");
        final List<String> content = ModuleReader.readFromFile(url.getFile());
        assertEquals(2, content.size());
        assertEquals("lala", content.get(0));
    }


}