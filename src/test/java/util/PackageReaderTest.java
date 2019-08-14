package util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class PackageReaderTest {

    @Test(expected = IOException.class)
    public void whenFileNotExistThenThrowException() throws IOException {
        ClusterReader.readFromFile("lalalala");
    }

    @Ignore
    @Test
    public void readTest() throws IOException {
        final List<String> content = ClusterReader.readFromFile("file.txt");

        assertEquals(2, content.size());
        assertEquals("lala", content.get(0));
    }

    @Test
    public void readFromPackageName() {


    }
}