package util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ClusterFileReader {

    public static List<String> read(final String fileName) throws IOException {
        return Files.lines(Paths.get(fileName), Charset.defaultCharset())
                .collect(Collectors.toList());

    }
}
