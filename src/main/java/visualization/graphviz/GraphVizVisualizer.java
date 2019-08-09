package visualization.graphviz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.common.collect.Table;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import visualization.DotFormatGenerator;

public class GraphVizVisualizer {

    private final DotFormatGenerator dotFormatGenerator;

    public GraphVizVisualizer(final DotFormatGenerator dotFormatGenerator) {
        this.dotFormatGenerator = dotFormatGenerator;
    }

    public void visualize(final Table<String, String, Integer> table) throws IOException {
        dotFormatGenerator.generate(table);
        final File file = new File("graphViz.dot");
        MutableGraph g = Parser.read(file);
        Graphviz.fromGraph(g).render(Format.SVG).toFile(new File("example/graphViz.svg"));

        g.graphAttrs()
                .add(Color.WHITE.gradient(Color.rgb("888888")).background().angle(90))
                .nodeAttrs().add(Color.WHITE.fill())
                .nodes().forEach(node ->
                node.add(
                        Color.named(node.name().toString()),
                        Style.lineWidth(4).and(Style.FILLED)));
        Graphviz.fromGraph(g).width(1000).render(Format.PNG).toFile(new File("example/ex5-2.png"));

    }

    public void generateGraphViz(final Table<String, String, Integer> clusters) throws FileNotFoundException, UnsupportedEncodingException {
        final GraphViz graphViz = new GraphViz();
        graphViz.addln(graphViz.start_graph());
        graphViz.addln("layout=\"sfdp\"");
        graphViz.addln("nodesep=\"3\"");
        graphViz.addln("ranksep=\"3\"");
        graphViz.addln("size=\"5000,5000\"");
        for (Table.Cell<String, String, Integer> cell : clusters.cellSet()) {
            if (cell.getValue() > 2) {

                try{
                    String row = cell.getRowKey().substring(cell.getRowKey().lastIndexOf("/") + 1, cell.getRowKey().lastIndexOf("."));
                    String column = cell.getColumnKey().substring(cell.getColumnKey().lastIndexOf("/") + 1, cell.getColumnKey().lastIndexOf("."));
                    graphViz.addln(row + "->" + column +"[ label = \"" + cell.getValue()+ "\"]");
                }catch (StringIndexOutOfBoundsException e){
                    System.out.println(cell.getRowKey());
                }

            }
        }
        graphViz.addln(graphViz.end_graph());
        graphViz.decreaseDpi();
        graphViz.decreaseDpi();
        File out = new File(graphViz.getImageDpi() + ".svg");
        PrintWriter writer = new PrintWriter("graphVizApp.dot", "UTF-8");
        writer.println(graphViz.getDotSource());
        writer.close();
        graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), "svg", "sfdp"), out);
    }

}
