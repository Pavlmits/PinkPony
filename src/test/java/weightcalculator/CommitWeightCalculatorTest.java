package weightcalculator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Table;
import model.Commit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommitWeightCalculatorTest {

    private final CommitWeightCalculator commitWeightCalculator = new CommitWeightCalculator();


    @Test
    public void calculateTest() {
        final Set<String> vertices = new HashSet<>();
        vertices.add("pavlina");
        vertices.add("aris");
        vertices.add("aris");
        vertices.add("nikos");

        final List<Commit> commitList = new ArrayList<>();
        final Commit commit = new Commit();
        final List<String> paths = new ArrayList<>();
        paths.add("aris");
        paths.add("pavlina");
        commit.setPaths(paths);
        commitList.add(commit);
        commitList.add(commit);

        final Table<String, String, Integer> table = commitWeightCalculator.calculate(vertices, commitList);

        assertEquals(Integer.valueOf(2), table.get("pavlina", "aris"));
    }
}