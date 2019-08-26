package clusteringlevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import exception.UnknownParameterException;
import filters.FilesFilter;
import graph.GraphCreator;
import model.ClusteringResult;
import model.Commit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import weightcalculator.CommitWeightCalculator;
import weightcalculator.WeightCalculator;

@RunWith(MockitoJUnitRunner.class)
public class FileLevelTest {

    private FilesFilter filesFilter = new FilesFilter();

    private GraphCreator graphCreator = new GraphCreator();

    private WeightCalculator weightCalculator = new CommitWeightCalculator();

    @InjectMocks
    private FileLevel fileLevel = new FileLevel(filesFilter, graphCreator, weightCalculator);

    private List<Commit> commitList;

    private List<String> paths1 = new ArrayList<>();

    private List<String> paths2 = new ArrayList<>();

    private List<String> paths3 = new ArrayList<>();

    private List<String> prefixs = new ArrayList<>();

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

        prefixs.add("Pav");
        prefixs.add("Xa");
        commitList.add(new Commit("1", paths1, new ArrayList<>()));
        commitList.add(new Commit("2", paths2, new ArrayList<>()));
        commitList.add(new Commit("3", paths3, new ArrayList<>()));
    }

    @Test
    public void whenPackagesAreEmptyListTest() throws UnknownParameterException {
        final ClusteringResult<String> actualResult = fileLevel.run("repo", commitList, new ArrayList<>(), "mr");
        assertEquals(18, actualResult.getWeightTable().size());
        assertEquals(7, actualResult.getFiles().size());
        assertFalse(actualResult.getClusters().isEmpty());

    }

    @Test
    public void extractFilesFromCommitsWhenNoCommits() {
        final Set<String> files = fileLevel.extractFilesFromCommits(new ArrayList<>(), new ArrayList<>());

        assertTrue(files.isEmpty());
    }

    @Test
    public void extractFileFromCommitsWhenCommitsExistsNoPackages() {
        final Set<String> files = fileLevel.extractFilesFromCommits(commitList, new ArrayList<>());

        assertEquals(7, files.size());
    }

    @Test
    public void extractFileFromCommitsWhenCommitsAndPackagesExists() {

        final Set<String> files = fileLevel.extractFilesFromCommits(commitList, prefixs);

        assertEquals(5, files.size());
    }


}