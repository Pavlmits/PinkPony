package visualization;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.Table;

public class JavascriptToolInputGenerator<V> {


    public void generate(final Table<V, V, Integer> table, final String folder) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("{\n\"nodes\":\n[\n");
        for (final V item : table.rowKeySet()) {
            generatedText.append("{\n\"nodeId\":\"").append(item).append("\",\n");
            generatedText.append("\"name\": \"").append(item).append("\",\n");
            generatedText.append("\"size\": \"").append(1).append("\"\n");
            generatedText.append("},\n");
        }
        generatedText.append("],\n");
        generatedText.append("\"relations\": \n[\n");
        for (Table.Cell cell : table.cellSet()) {
            generatedText.append("{\n");
            generatedText.append("\"nodeidA\":\"").append(cell.getRowKey()).append("\",\n");
            generatedText.append("\"nodeidB\":\"").append(cell.getColumnKey()).append("\",\n");
            generatedText.append("\"strength\":\"").append(cell.getValue()).append("\",\n");
            generatedText.append("},\n");
        }
        generatedText.append("]\n");
        generatedText.append("}");

        PrintWriter writer = new PrintWriter(folder + "/jsonFile.json", "UTF-8");
        writer.println(generatedText);
        writer.close();

    }
}
