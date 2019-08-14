package clustering;

import static org.junit.Assert.*;

import exception.UnknownParameterException;
import graph.GraphCreator;
import org.junit.Test;

public class ClusteringFactoryTest {



    @Test(expected = UnknownParameterException.class)
    public void whenEmptyStringThenTrowExceptionTest() throws UnknownParameterException {
        ClusteringFactory.getClustering("", new GraphCreator());
    }

    @Test
    public void whenMarkovStringPassThenReturnMarkovInstance() throws UnknownParameterException {
        final Clustering clustering = ClusteringFactory.getClustering("mr", new GraphCreator());
        assertSame(clustering.getClass(), MarkovSimpleClustering.class);
    }

    @Test
    public void whenMaxStringPassThenReturnMaxInstance() throws UnknownParameterException {
        final Clustering clustering = ClusteringFactory.getClustering("max", new GraphCreator());
        assertSame(clustering.getClass(), MaxMaxClustering.class);
    }

    @Test(expected = UnknownParameterException.class)
    public void whenInvalidStringThenTrowExceptionTest() throws UnknownParameterException {
        ClusteringFactory.getClustering("pavlina", new GraphCreator());
    }
}