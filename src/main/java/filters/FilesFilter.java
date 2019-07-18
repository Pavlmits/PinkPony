package filters;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FilesFilter {

    @Inject
    public FilesFilter() {
    }

    public List<String> filterAll(final List<String> filesList){
        return filesList.stream()
                .filter(file -> !file.endsWith(".csv"))
                .filter(file -> !file.endsWith(".txt"))
                .filter(file -> !file.endsWith(".xml"))
                .filter(file -> !file.endsWith(".md"))
                .filter(file -> !file.equals("/dev/null"))
                .filter(file -> !file.endsWith(".lock"))
                .filter(file -> !file.endsWith(".tokens"))
                .collect(Collectors.toList());
    }
}
