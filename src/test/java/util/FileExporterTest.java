package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Table;
import org.junit.Ignore;
import org.junit.Test;

public class FileExporterTest {

    private FileHandler<String> fileExporter = new FileHandler<>();

    @Test
    public void exportStringTest() throws IOException {
        Collection<String> subData1 = Arrays.asList("test1", "test2", "test3");
        Collection<String> subData2 = Arrays.asList("test1", "test2", "test3");
        Collection<Collection<String>> data = Arrays.asList(subData1, subData2);
        String fileName = "testFile.txt";
        fileExporter.export(data, fileName);
        boolean actual = Files.exists(Files.createTempFile(fileName, ".txt"));
        assertTrue(actual);
        File file = new File(fileName);
        file.delete();
    }

    @Test
    public void generateFolderNameTest(){
        final String path = "\\dev\\ojAlgo\\.git";

        final String actual = FileHandler.generateFolderName(path);

        assertEquals("ojAlgo", actual);
    }

    @Test
    public void readTableTest() throws IOException {
        final Table<String, String, Integer> table = FileHandler.readTable("table.txt");

        assertTrue(!table.isEmpty());
    }

}