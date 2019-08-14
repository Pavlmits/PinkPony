package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

public class FileExporterTest {

    private FileExporter<String> fileExporter = new FileExporter<>();

    @Ignore
    @Test
    public void exportStringTest() throws IOException {
        Collection<String> subData1 = Arrays.asList("test1", "test2", "test3");
        Collection<String> subData2 = Arrays.asList("test1", "test2", "test3");
        Collection<Collection<String>> data = Arrays.asList(subData1, subData2);
        String fileName = "test/resources/testFile.txt";
        fileExporter.export(data, fileName);
        boolean actual = Files.exists(Files.createTempFile(fileName, ".txt"));
        assertTrue(actual);
    }

    @Test
    public void generateFolderNameTest(){
        final String path = "C:\\dev\\ojAlgo\\.git";

        final String actual = FileExporter.generateFolderName(path);

        assertEquals("ojAlgo", actual);
    }

}