package visualization.graphviz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class DotFormatGenerator {

    public static final String REGEX_DOT_GRAPH = "\"(.*)\"->\"([^]/]*)\".*(\\d)";

    public Table<String, String, Integer> dotToTableGenerator(final String fileName) throws IOException {
        final Table<String, String, Integer> weightTable = HashBasedTable.create();
        final Path path = Paths.get(fileName);
        Pattern regex = Pattern.compile(REGEX_DOT_GRAPH);
        final List<String> lines = Files.lines(path).collect(Collectors.toList());
        for (final String line : lines) {
            final Matcher matcher = regex.matcher(line);
            if (matcher.matches()) {
                weightTable.put(matcher.group(1), matcher.group(2), Integer.valueOf(matcher.group(3)));
            }
        }

        return weightTable;
    }

}
