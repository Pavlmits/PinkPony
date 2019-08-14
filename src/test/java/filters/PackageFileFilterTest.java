package filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Package;
import org.junit.Test;

public class PackageFileFilterTest {

    private final ClusterFileFilter clusterFileFilter = new ClusterFileFilter();


    @Test
    public void filterEmptyList() {
        final List<String> files = new ArrayList<>();
        final List<String> targetPrefixes = new ArrayList<>();

        final List<String> actual = clusterFileFilter.filter(files, targetPrefixes);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void filteredWithPrefixes() {
        final List<String> files = new ArrayList<>();
        final List<String> targetPrefixes = new ArrayList<>();

        files.add("Pavlina/pa");
        files.add("Xaris/pa");
        files.add("Pavlina/pa");
        targetPrefixes.add("Pavlina");

        final List<String> actual = clusterFileFilter.filter(files, targetPrefixes);

        assertEquals(2, actual.size());
    }

    @Test
    public void filterAndReturnClustersEmptySet() {
        final Set<String> files = new HashSet<>();
        final List<String> targetPrefixes = new ArrayList<>();

        final Set<Package> actual = clusterFileFilter.filterAndReturnClusters(files, targetPrefixes);

        assertTrue(actual.isEmpty());

    }

    @Test
    public void filterAndReturn2ClustersTest() {
        final Set<String> files = new HashSet<>();
        final List<String> targetPrefixes = new ArrayList<>();

        files.add("Pavlina/pa");
        files.add("Xaris/pa");
        files.add("Pavlina/pav");
        files.add("Nikos/pav");
        targetPrefixes.add("Pavlina");
        targetPrefixes.add("Xaris");

        final Set<Package> actual = clusterFileFilter.filterAndReturnClusters(files, targetPrefixes);

        assertFalse(actual.isEmpty());
        assertEquals(2, actual.size());
        assertEquals("Pavlina", actual.iterator().next().getName());
        assertEquals(2, actual.iterator().next().getFiles().size());

    }

}