package filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class ClusterFilterTest {


    private ClusterFilter<String> clusterFilter = new ClusterFilter<>();

    @Test
    public void whenEmptyCollectionThenReturnSameTest() {
        final Collection<Collection<String>> clusters;
        clusters = new ArrayList<>();
        clusters.add(new ArrayList<>());
        clusters.add(new ArrayList<>());

        final Collection<Collection<String>> actual = clusterFilter.filter(clusters);

        assertEquals(2, actual.size());
        actual.forEach((n) -> assertTrue(n.isEmpty()));

    }

    @Test
    public void whenMoreThan15FilesThenRemoveThem() {
        final Collection<Collection<String>> clusters = new ArrayList<>();
        final Collection<String> list1 = new ArrayList<>();
        final Collection<String> list2 = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            list1.add("A");
        }
        for (int i = 0; i < 5; i++) {
            list2.add("B");
        }

        clusters.add(list1);
        clusters.add(list2);
        clusters.add(new ArrayList<>());

        final Collection<Collection<String>> actual = clusterFilter.filter(clusters);

        assertEquals(2, actual.size());
    }

}