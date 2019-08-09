package visualization;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import util.FileExporter;


public class ClassDiagramGenerator {

    public static void generate(final List<Collection<String>> clusters) throws IOException {
        String source = "@startuml\nscale 1\n";
        int count = 0;
        for (final Collection<String> cluster : clusters) {
            source += "package \"" + count + "\"  #DDDDDD { \n";
            for (final String s : cluster) {
                int lastIndex = s.lastIndexOf("/");
                int indexOfFullStop = s.lastIndexOf(".");
                String substring;
                try{
                  substring  = s.substring(lastIndex + 1, indexOfFullStop);

                }catch (StringIndexOutOfBoundsException e){
                    System.out.println(s);
                    substring = "";
                }
                if (!substring.isBlank()) {
                    source += "class " + substring + "\n";

                }
            }
            count++;
            source += "}\n";
//            if (count % 4 == 0) {
//                source += "newpage\n";
//            }
        }
        source += "@enduml\n";
        PrintWriter writer = new PrintWriter("uml.txt", "UTF-8");
        writer.println(source);
        writer.close();
        SourceStringReader reader = new SourceStringReader(source);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
        os.close();
        final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
        FileExporter.exportSvg(svg);
    }

    public static void runPlu() throws IOException {
        Runtime re = Runtime.getRuntime();
        BufferedReader output = null;
        try {
            final Process cmd = re.exec("java -jar plantuml.jar" + " uml.txt");
            output = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        String resultOutput = output.readLine();
        System.out.println(resultOutput);
    }
}
