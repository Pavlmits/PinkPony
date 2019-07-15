package filters;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class FilesFilterTest {

    private List<String> fileList;

    @InjectMocks
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

}