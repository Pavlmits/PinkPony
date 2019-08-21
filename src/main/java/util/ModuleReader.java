package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModuleReader {

    public static List<String> readFromFile(final String fileName) throws IOException {
        return Files.lines(Paths.get(fileName), Charset.defaultCharset())
                .collect(Collectors.toList());

    }

    public List<String> readFromPackageName(final String packageName, final String repo) {
        final File file = new File(repo.substring(0, repo.lastIndexOf('\\') + 1) + packageName);
        final List<String> packages = Arrays.asList(Objects.requireNonNull(file.list((current, name) -> new File(current, name).isDirectory())));
        packages.replaceAll(pack -> packageName + "/" + pack);
        return packages;
    }

}
