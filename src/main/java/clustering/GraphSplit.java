package clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.graph.MutableValueGraph;

public class GraphSplit<T, W> {

    public void split(final MutableValueGraph<T, W> graph, int k) {
        final List<MutableValueGraph<T, W>> graphList = new ArrayList<>();
        final Set<T> stringSet = graph.nodes();
        for (final T targetNode : stringSet) {


        }

    }

    private void keepKHighet(final MutableValueGraph<T, W> graph, final T targetNode) {
        final Set<T> connectedNodes = graph.adjacentNodes(targetNode);

        if (!connectedNodes.isEmpty()) {
            for (T connectedNode : connectedNodes) {

            }
        }
    }
}
