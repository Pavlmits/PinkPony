package visualization;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

public class GraphVisualizer<V> {


    public void visualize(Graph<V, DefaultEdge> graph) throws IOException {

        JGraphXAdapter<V, DefaultEdge> graphAdapter = new JGraphXAdapter<>(graph);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        save(graphAdapter);
    }

    private void save(JGraphXAdapter<V, DefaultEdge> graphAdapter) throws IOException {
        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imageFile = new File("src/main/resources/graph.png");
        ImageIO.write(image, "PNG", imageFile);
    }

    public  void visualize2(Graph<V, DefaultEdge> graph){
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
//        JGraph jgraph = new JGraph(new JGraphModelAdapter(graph));
//        frame.getContentPane().add(jgraph);
        frame.setVisible(true);
    }
}