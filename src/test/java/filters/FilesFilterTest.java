package filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FilesFilterTest {

    private List<String> fileList;

    private FilesFilter filesFilter = new FilesFilter();

    @Before
    public void setUp() {
        fileList = new ArrayList<>();
    }

    @Test
    public void filterAllTest() {
        fileList.add("file.java");
        fileList.add("file.csv");
        fileList.add("file.txt");

        final List<String> actual = filesFilter.filterAll(fileList);

        assertEquals(1, actual.size());

    }

    @Test
    public void filterEmptyList() {
        final List<String> actual = filesFilter.filterAll(fileList);

        assertEquals(0, actual.size());
    }

    @Test
    public void filterBasedOnPackageEmptyList() {
        final List<String> fileList = new ArrayList<>();
        final String regex = "Hello";
        final List<String> regexs = new ArrayList<>();
        regexs.add(regex);

        final List<String> actual = filesFilter.filterBasedOnPackage(fileList, regexs);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void filterBasedOnPackageEmptyRegex() {
        final List<String> fileList = new ArrayList<>();
        final String regex = "";
        final List<String> regexs = new ArrayList<>();
        regexs.add(regex);

        fileList.add("hello");
        fileList.add("ok");

        final List<String> actual = filesFilter.filterBasedOnPackage(fileList, regexs);

        assertEquals(2, actual.size());

    }

    @Test
    public void filterBasedOnPackageTest(){
        final List<String> fileList = new ArrayList<>();
        final String regex = "pav";
        final List<String> regexs = new ArrayList<>();
        regexs.add(regex);
        fileList.add("pav/1");
        fileList.add("pav2");
        fileList.add("arisl");

        final List<String> actual = filesFilter.filterBasedOnPackage(fileList, regexs);

        assertEquals(2 ,actual.size());
        assertEquals("pav/1", actual.get(0));


    }


}