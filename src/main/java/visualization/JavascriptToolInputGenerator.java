package visualization;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import com.google.common.collect.Table;

public class JavascriptToolInputGenerator {


    public void generate(final Set<String> files, final Table<String, String, Integer> table) throws FileNotFoundException, UnsupportedEncodingException {
        final StringBuilder generatedText = new StringBuilder();
        generatedText.append("{\n\"nodes\":\n[\n");
        for (final String file : files) {
            generatedText.append("{\n\"nodeId\":\"").append(file).append("\",\n");
            generatedText.append("\"name\": \"").append(file).append("\",\n");
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

        PrintWriter writer = new PrintWriter("jsonFile.json", "UTF-8");
        writer.println(generatedText);
        writer.close();

    }
}
