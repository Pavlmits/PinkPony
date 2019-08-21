package util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModuleReader {

    public List<String> readFromPackageName(final String packageName, final String repo) {
        final File file = new File(repo.substring(0, repo.lastIndexOf('\\') + 1) + packageName);
        final List<String> packages = Arrays.asList(Objects.requireNonNull(file.list((current, name) -> new File(current, name).isDirectory())));
        packages.replaceAll(pack -> packageName + "/" + pack);
        return packages;
    }

}
