package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Table;

public class TableToTxt {

    public void export(final Table<String, String, Integer> table) throws IOException {
        final Path file = Paths.get("graph.txt");
        final List<String> lines = new ArrayList<>();
        for (Table.Cell<String, String, Integer> cell : table.cellSet()) {
            lines.add(cell.getRowKey() + "\t" + cell.getColumnKey() + "\t" + cell.getValue());
        }
        Files.write(file, lines, StandardCharsets.UTF_8);
    }
}
