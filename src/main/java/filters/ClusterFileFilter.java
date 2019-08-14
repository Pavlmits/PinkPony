package filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Package;
import org.apache.commons.lang3.StringUtils;

public class ClusterFileFilter {

    public List<String> filter(final List<String> files, List<String> targetPrefixes) {
        final List<String> filteredFiles = new ArrayList<>();
        for (final String file : files) {
            if (StringUtils.startsWithAny(file, targetPrefixes.toArray(new String[0]))) {
                filteredFiles.add(file);
            }
        }
        return filteredFiles;
    }

    public Set<Package> filterAndReturnClusters(final Set<String> files, List<String> targetPrefixes){
        final Set<Package> packageSet = new HashSet<>();
        for (final String targetPrefix : targetPrefixes) {
            final List<String> targetFiles = files.stream()
                    .filter(file -> file.startsWith(targetPrefix))
                    .collect(Collectors.toList());
            packageSet.add(new Package(targetPrefix, targetFiles));
        }

        return packageSet;
    }
}
