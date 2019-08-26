package clusteringlevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import exception.UnknownParameterException;
import graph.GraphCreator;
import model.ClusteringResult;
import model.Commit;
import model.Module;
import org.junit.Before;
import org.junit.Test;
import weightcalculator.ModuleWeightCalculator;
import weightcalculator.WeightCalculator;

public class ModuleLevelTest {

    private List<Commit> commitList;

    private List<String> paths1 = new ArrayList<>();

    private List<String> paths2 = new ArrayList<>();

    private List<String> paths3 = new ArrayList<>();

    private List<String> prefixs = new ArrayList<>();
    private GraphCreator graphCreator = new GraphCreator();

    private WeightCalculator weightCalculator = new ModuleWeightCalculator();


    private ModuleLevel moduleLevel = new ModuleLevel(graphCreator, weightCalculator);


    @Before
    public void setup() {
        commitList = new ArrayList<>();
        paths1.add("Pa/1");
        paths1.add("Pa/2");
        paths1.add("Xa/3");

        paths2.add("Xa/3");
        paths2.add("Xa/1");
        paths2.add("NM/1");
        paths2.add("NM/2");

        paths3.add("Pa/1");
        paths3.add("Pa/2");
        paths3.add("Pa/3");
        paths3.add("Xa/3");
        paths3.add("Xa/1");
        paths3.add("NM/1");
        paths3.add("NM/2");

        prefixs.add("Pa");
        prefixs.add("Xa");
        commitList.add(new Commit("1", paths1, new ArrayList<>()));
        commitList.add(new Commit("2", paths2, new ArrayList<>()));
        commitList.add(new Commit("3", paths3, new ArrayList<>()));
    }


    @Test
    public void whenPackagesAndCommitsAreEmpty() throws UnknownParameterException {
        final ClusteringResult<Module> actualResult = moduleLevel.run("repo", new ArrayList<>(), new ArrayList<>(), "mr");

        assertTrue(actualResult.getFiles().isEmpty());
        assertTrue(actualResult.getWeightTable().isEmpty());
        assertTrue(actualResult.getClusters().isEmpty());
    }

    @Test
    public void whenPackagesIsAnEmptyList() throws UnknownParameterException {
        final ClusteringResult<Module> actualResult = moduleLevel.run("repo", commitList, new ArrayList<>(), "max");

        //assertFalse(actualResult.getFiles().isEmpty());
        assertTrue(actualResult.getClusters().isEmpty());
    }


    @Test
    public void runTest() throws UnknownParameterException {
        final List<String> packages = new ArrayList<>();
        packages.add("Pa");
        packages.add("NM");
        final ClusteringResult<Module> actualResult = moduleLevel.run("repo", commitList, packages, "ch");

        assertFalse(actualResult.getClusters().isEmpty());

    }

}