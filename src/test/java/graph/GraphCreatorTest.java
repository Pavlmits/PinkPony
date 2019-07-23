package graph;

import static org.junit.Assert.*;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

public class GraphCreatorTest {

    private final Table<String, String, Integer> table = HashBasedTable.create();

    private final GraphCreator<String> graphCreator = new GraphCreator<>();



    @Test
    public void createEmptyJGraphTest(){
        final Graph<String, DefaultWeightedEdge> actual = graphCreator.createSimpleWightedGraph(table);

        assertTrue(actual.edgeSet().isEmpty());
    }

    @Test
    public void createJGraphTest(){
        table.put("a", "b", 1);
        table.put("b", "d", 2);
        table.put("d", "a", 2);
        table.put("a", "c", 1);
        table.put("c", "b", 4);

        final Graph<String, DefaultWeightedEdge> actual = graphCreator.createSimpleWightedGraph(table);


        assertEquals(5, actual.edgeSet().size());
        assertEquals(4, actual.vertexSet().size());
        assertEquals(1, actual.getAllEdges("a", "b").size());


    }

    @Test
    public void createEmptyGuavaGraph(){

        final MutableValueGraph<String, Integer> actual = graphCreator.create(table);

        assertEquals(0, actual.edges().size());
        assertEquals(0, actual.nodes().size());
    }

    @Test
    public void createGuavaGraph(){
        table.put("a", "b", 1);
        table.put("b", "d", 2);
        table.put("d", "a", 2);
        table.put("a", "c", 1);
        table.put("c", "b", 4);
        final MutableValueGraph<String, Integer> actual = graphCreator.create(table);

        assertEquals(4, actual.nodes().size());
    }


}