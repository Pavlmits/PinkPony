package clusteringlevel;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import exception.UnknownParameterException;
import filters.FilesFilter;
import graph.GraphCreator;
import model.Commit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import weightcalculator.WeightCalculator;

public class FileLevelTest {

    @Mock
    private FilesFilter filesFilter;

    @Mock
    private GraphCreator graphCreator;

    @Mock
    private WeightCalculator weightCalculator;

    @InjectMocks
    private FileLevel fileLevel;

    private List<Commit> commitList;

    private List<String> paths1 = new ArrayList<>();

    private List<String> paths2 = new ArrayList<>();

    private List<String> paths3 = new ArrayList<>();

    @Before
    public void setup() {
        commitList = new ArrayList<>();
        paths1.add("Pav/1");
        paths1.add("Pav/2");
        paths1.add("Xa/3");

        paths2.add("Xa/3");
        paths2.add("Xa/1");
        paths2.add("NM/1");
        paths2.add("NM/2");

        paths3.add("Pav/1");
        paths3.add("Pav/2");
        paths3.add("Pav/3");
        paths3.add("Xa/3");
        paths3.add("Xa/1");
        paths3.add("NM/1");
        paths3.add("NM/2");
        commitList.add(new Commit("1", paths1));
        commitList.add(new Commit("2", paths2));
        commitList.add(new Commit("3", paths3));
    }

    @Ignore("I have to finish it")
    @Test
    public void whenPackagesAreEmptyListTest() throws UnknownParameterException {
        when(filesFilter.filterAll(paths1)).thenReturn(paths1);
        when(filesFilter.filterAll(paths2)).thenReturn(paths2);
        when(filesFilter.filterAll(paths3)).thenReturn(paths3);

        fileLevel.run("repo", commitList, new ArrayList<>(), "mr");
    }

}