package visualization;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.parse.Parser;

public class GraphVizVisualizer {

    public  void visualize(/*final Collection<String> cluster*/) throws IOException {
        MutableGraph g = Parser.read(getClass().getResourceAsStream("/color.dot"));
        Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-1.png"));

        g.graphAttrs()
                .add(Color.WHITE.gradient(Color.rgb("888888")).background().angle(90))
                .nodeAttrs().add(Color.WHITE.fill())
                .nodes().forEach(node ->
                node.add(
                        Color.named(node.name().toString()),
                        Style.lineWidth(4).and(Style.FILLED)));
        Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-2.png"));
//        for (final String s : cluster) {
//        }
    }

    public static void visualize1() throws IOException {
        Node width = node("Very long node labels can go over the border");
        Node center = node(Label.html("HTML labels on the other side, can get uncentered"));
        Graphviz g = Graphviz.fromGraph(graph()
                .nodeAttr().with(Font.name("casual"), Shape.RECTANGLE)
                .with(width.link(center)));
        g.render(Format.PNG).toFile(new File("example/ex6d.png"));
        g.fontAdjust(.87).render(Format.PNG).toFile(new File("example/ex6a.png"));
    }
}
