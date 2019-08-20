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

    @Test
    public void whenWatsetStringPassThenReturnWatsetInstance() throws UnknownParameterException {
        final Clustering clustering = ClusteringFactory.getClustering("watset", new GraphCreator());
        assertSame(clustering.getClass(), WatsetClustering.class);
    }

    @Test
    public void whenChStringPassThenReturnChInstance() throws UnknownParameterException {
        final Clustering clustering = ClusteringFactory.getClustering("ch", new GraphCreator());
        assertSame(clustering.getClass(), ChineseWhispersClustering.class);
    }

    @Test(expected = UnknownParameterException.class)
    public void whenInvalidStringThenTrowExceptionTest() throws UnknownParameterException {
        ClusteringFactory.getClustering("pavlina", new GraphCreator());
    }
}