package visualization;

import static org.graphstream.stream.file.FileSinkImages.OutputType.png;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.SourceStringReader;

public class ClassDiagramGenerator {

    public static void generate() throws IOException {
        OutputStream out = new FileOutputStream("myNewPngFile.png");
        String source = "@startuml\n";
        source += "Bob -> Alice : hello\n";
        source += "@enduml\n";
        SourceStringReader reader = new SourceStringReader(source);
        String desc = reader.generateImage(out);
    }
}
