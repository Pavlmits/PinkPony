package weightcalculator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import model.Package;
import model.Commit;
import org.junit.Before;
import org.junit.Test;

public class ClusterWeightCalculatorTest {

    private ClusterWeightCalculator packageWeightCalculator;

    @Before
    public void setup(){
        packageWeightCalculator = new ClusterWeightCalculator();
    }

    @Test
    public void calculateTest() {
        final Set<Package> packageSet = new HashSet<>();
        final List<String> files1 = new ArrayList<>();
        final List<String> files2 = new ArrayList<>();
        final List<String> files3 = new ArrayList<>();
        final List<String> paths1 = new ArrayList<>();
        final List<String> paths2 = new ArrayList<>();
        final List<String> paths3 = new ArrayList<>();
        final List<Commit> commitList = new ArrayList<>();

        files1.add("Pav/1");
        files1.add("Pav/2");
        files1.add("Pav/3");

        files2.add("Xa/1");
        files2.add("Xa/2");
        files2.add("Xa/3");

        files3.add("NM/1");
        files3.add("NM/2");
        files3.add("NM/3");

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


        final Package package1 = new Package("Pav", files1);
        final Package package2 = new Package("Xa", files2);
        final Package package3 = new Package("NM", files3);

        packageSet.add(package1);
        packageSet.add(package2);
        packageSet.add(package3);
        commitList.add(new Commit("1", paths1));
        commitList.add(new Commit("2", paths2));
        commitList.add(new Commit("3", paths3));

        final Table<Package, Package, Integer> actual = packageWeightCalculator.calculate(packageSet, commitList);

        assertEquals(3,actual.size());
        assertEquals(2 ,actual.get(package1, package2).intValue());
        assertEquals(1, actual.get(package1, package3).intValue());
        assertEquals(2, actual.get(package2, package3).intValue());
    }

}