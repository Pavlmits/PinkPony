package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class FilesPrefixListCheckerTest {

    @Test
    public void isFileInTheListWithPrefix() {
        List<String> files = new ArrayList<>(Arrays.asList("testOne", "testTwo"));
        List<String> prefix = new ArrayList<>(Collections.singletonList("test"));
        boolean actual = FilesPrefixListChecker.isInTheList(files, prefix);
        assertTrue(actual);
    }

    @Test
    public void FileNotInTheListWithPrefix() {
        List<String> files = new ArrayList<>(Arrays.asList("realOne", "realTwo"));
        List<String> prefix = new ArrayList<>(Collections.singletonList("test"));
        boolean actual = FilesPrefixListChecker.isInTheList(files, prefix);
        assertFalse(actual);
    }

    @Test
    public void whenEmptyPrefixListThen(){
        List<String> files = new ArrayList<>(Arrays.asList("testOne", "testTwo"));
        List<String> prefix = new ArrayList<>();
        boolean actual = FilesPrefixListChecker.isInTheList(files, prefix);
        assertFalse(actual);
    }

    @Test
    public void whenEmptyFilesListThen(){
        List<String> files = new ArrayList<>();
        List<String> prefix = new ArrayList<>(Collections.singleton("test"));
        boolean actual = FilesPrefixListChecker.isInTheList(files, prefix);
        assertFalse(actual);
    }

    @Test
    public void whenBothFilesAndPrefixEmptyListThen(){
        List<String> files = new ArrayList<>();
        List<String> prefix = new ArrayList<>();
        boolean actual = FilesPrefixListChecker.isInTheList(files, prefix);
        assertFalse(actual);
    }


}