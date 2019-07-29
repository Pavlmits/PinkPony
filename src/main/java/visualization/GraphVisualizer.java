package visualization;


public class GraphVisualizer<V> {


//    public void visualize(Graph<V, DefaultEdge> graph) throws IOException {
//
//        JGraphXAdapter<V, DefaultEdge> graphAdapter = new JGraphXAdapter<>(graph);
//        MxIGraphLayout layout = new mxCircleLayout(graphAdapter);
//        layout.execute(graphAdapter.getDefaultParent());
//        save(graphAdapter);
//    }
//
//    private void save(JGraphXAdapter<V, DefaultEdge> graphAdapter) throws IOException {
//        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
//        File imageFile = new File("src/main/resources/graph.png");
//        ImageIO.write(image, "PNG", imageFile);
//    }
}