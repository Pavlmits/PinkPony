package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.graph.MutableValueGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class InterconnectivityCalculator {

    public int calculateRelative() {
        return 0;
    }


    public Map<String, Integer> calculateEC(final MutableValueGraph<String, Integer> graph) {
        final Map<String, Integer> map = new HashMap<>();
        final Set<String> stringSet = graph.nodes();
        for (final String targetNode : stringSet) {
            int sumWeight = 0;
            final Set<String> connectedNodes = graph.adjacentNodes(targetNode);
            for (String connectedNode : connectedNodes) {
                sumWeight += graph.edgeValue(targetNode, connectedNode);

            }
        }
        return map;
    }

    private List<DefaultWeightedEdge> getAllEdgesFromVertice(final MutableValueGraph<String, Integer> graph, final String vertex) {
        return new ArrayList<>();
    }


}
