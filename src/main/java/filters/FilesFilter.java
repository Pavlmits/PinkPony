package filters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesFilter {

    public List<String> filter(List<String> filesList) {
        final List<String> filteredFiles = new ArrayList<>();
        for (String file : filesList) {
            if (!file.endsWith(".csv")) {
                filteredFiles.add(file);
            }
        }
        return filteredFiles;
    }

    public List<String> filterAll(final List<String> filesList){
        return filesList.stream()
                .filter(file -> !file.endsWith(".csv"))
                .filter(file -> !file.endsWith(".xml"))
                .filter(file -> !file.endsWith(".md"))
                .filter(file -> !file.equals("/dev/null"))
                .filter(file -> !file.endsWith(".lock"))
                .filter(file -> !file.endsWith(".tokens"))
                .collect(Collectors.toList());
    }
}