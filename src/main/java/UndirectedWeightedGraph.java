public class UndirectedWeightedGraph {

    private int[][] edges;  // adjacency matrix
    private Object[] labels;

    public UndirectedWeightedGraph(int n) {
        edges = new int[n][n];
        labels = new Object[n];
    }

    public int size() {
        return labels.length;
    }

    public void setLabel(int vertex, Object label) {
        labels[vertex] = label;
    }

    public Object getLabel(int vertex) {
        return labels[vertex];
    }

    public void addEdge(int source, int target, int weight) {
        edges[source][target] = weight;

    }

    public boolean isEdge(int source, int target) {
        return edges[source][target] > 0;
    }

    public void removeEdge(int source, int target) {
        edges[source][target] = 0;
    }

    public int getWeight(int source, int target) {
        return edges[source][target];
    }

    public int[] neighbors(int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) count++;
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) answer[count++] = i;
        }
        return answer;
    }
}
