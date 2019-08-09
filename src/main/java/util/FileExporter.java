package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import model.Cluster;

public class FileExporter {

    public static void export(final Collection<Collection<String>> data, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (final Collection<String> cluster : data) {
            cluster.forEach(writer::println);
            writer.println("----------------------------");
        }
        writer.close();
    }

    public static void exportClusters(final Collection<Collection<Cluster>> data, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (final Collection<Cluster> cluster : data) {
            cluster.forEach(writer::println);
            writer.println("----------------------------");
        }
        writer.close();
    }

    public static void exportSvg(final String svgString) throws FileNotFoundException {
        final PrintWriter printWriter = new PrintWriter("graph.svg");
        printWriter.println(svgString);
        printWriter.close();
    }
}
