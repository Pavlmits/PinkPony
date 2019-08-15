package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.io.FileUtils;

public class FileHandler<V> {

    public void export(final Collection<Collection<V>> data, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (final Collection<V> cluster : data) {
            cluster.forEach(writer::println);
            writer.println("----------------------------");
        }
        writer.close();
    }

    public void exportTable(final Table<V, V, Integer> table, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (Table.Cell cell : table.cellSet()) {
            writer.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
        }
        writer.close();
    }

    public Table<String, String, Integer> readTable(final String fileName) throws IOException {
        final Table<String, String, Integer> table = HashBasedTable.create();
        final Path path = Paths.get(fileName);
        final List<String> lines = Files.lines(path).collect(Collectors.toList());
        for (final String line : lines) {
            final String[] array = line.split(" ");
            table.put(array[0], array[1], Integer.valueOf(array[2]));
        }
        return table;
    }


    public static void exportSvg(final String svgString) throws FileNotFoundException {
        final PrintWriter printWriter = new PrintWriter("graph.svg");
        printWriter.println(svgString);
        printWriter.close();
    }

    public static String generateFolderName(final String path) {
        Pattern pattern = Pattern.compile("\\\\([^\\\\]*?)\\\\.git");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "not Valid";
    }

    //TODO fix problem recreation folder
    public static void createFolder(final String folderName) {
        File f = new File(folderName);
        final boolean mkdir = f.mkdir();
        if (!mkdir) {
            try {

                FileUtils.cleanDirectory(f); //clean out directory (this is optional -- but good know)
                FileUtils.forceDelete(f); //delete directory
                FileUtils.forceMkdir(f); //create directory
            } catch (IOException e) {
                System.out.println("Can not create directory");
            }
        }

    }

}
