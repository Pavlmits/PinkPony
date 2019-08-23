package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.io.FileUtils;

public class FileHandler<V> {

    public void export(final Collection<Collection<V>> data, final String file) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        for (final Collection<V> cluster : data) {
            cluster.forEach(writer::println);
            writer.println("----------------------------");
        }
        writer.close();
    }

    public void exportWithClusterPrefix(final Collection<Collection<V>> data, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        int count = 1;
        for (final Collection<V> cluster : data) {
            final int finalCount = count;
            cluster.forEach(c -> writer.println("Cluster" + finalCount + File.separator + c));
            count++;
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

    public static Table<String, String, Integer> readTable(final InputStream file) throws IOException {
        final Table<String, String, Integer> table = HashBasedTable.create();
        final BufferedReader br = new BufferedReader(new InputStreamReader(file));
        final List<String> lines = br.lines().collect(Collectors.toList());
        for (final String line : lines) {
            final String[] array = line.split(" ");
            table.put(array[0], array[1], Integer.valueOf(array[2]));
        }
        return table;
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
