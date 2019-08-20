package weightcalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import model.Commit;
import model.Module;
import org.junit.Test;

public class ModuleWeightCalculatorTest {

    private ModuleWeightCalculator moduleWeightCalculator = new ModuleWeightCalculator();


    @Test
    public void calculateTest() {
        final Set<Module> moduleSet = new LinkedHashSet<>();
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


        final Module module1 = new Module("Pav", files1);
        final Module module2 = new Module("Xa", files2);
        final Module module3 = new Module("NM", files3);

        moduleSet.add(module1);
        moduleSet.add(module2);
        moduleSet.add(module3);
        commitList.add(new Commit("1", paths1));
        commitList.add(new Commit("2", paths2));
        commitList.add(new Commit("3", paths3));

        final Table<Module, Module, Integer> actualTable = moduleWeightCalculator.calculate(moduleSet, commitList);

        assertEquals(3, actualTable.size());
        assertThat(actualTable.get(module1, module2)).isEqualTo(2);
        assertThat(actualTable.get(module1, module3)).isEqualTo(1);
        assertThat(actualTable.get(module2, module3)).isEqualTo(2);
    }

}