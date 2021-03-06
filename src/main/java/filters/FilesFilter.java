package filters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesFilter {

    //TODO readFromFile from file
    public List<String> filterAll(final List<String> filesList) {
        return filesList.stream()
                .filter(file -> !file.endsWith(".csv"))
                .filter(file -> !file.endsWith(".zip"))
                .filter(file -> !file.endsWith(".txt"))
//                .filter(file -> !file.endsWith(".xml"))
                .filter(file -> !file.endsWith(".md"))
                .filter(file -> !file.equals("/dev/null"))
                .filter(file -> !file.endsWith(".lock"))
                .filter(file -> !file.endsWith(".tokens"))
                .filter(file -> !file.startsWith(".gitignore"))
                .collect(Collectors.toList());
    }

    public List<String> filterBasedOnPackage(final List<String> fileList, final List<String> prefixs) {
        List<String> files = new ArrayList<>();
        for (final String regex : prefixs) {
            files.addAll(fileList.stream()
                    .filter(file -> file.startsWith(regex))
                    .collect(Collectors.toList()));
        }
        return files;

    }

    public List<String> getFilteredList(final List<String> fileList, final List<String> prefixs) {
        return prefixs.isEmpty() ? fileList : filterBasedOnPackage(fileList, prefixs);
    }

}

