package clusteringlevel;

import static org.junit.Assert.assertSame;

import exception.UnknownParameterException;
import org.junit.Test;

public class ClusteringLevelFactoryTest {

    @Test(expected = UnknownParameterException.class)
    public void whenEmptyStringThenTrowExceptionTest() throws UnknownParameterException {
        ClusteringLevelFactory.getClusteringLevel("");
    }

    @Test
    public void whenMarkovStringPassThenReturnMarkovInstance() throws UnknownParameterException {
        final ClusteringLevel level = ClusteringLevelFactory.getClusteringLevel("pack");
        assertSame(level.getClass(), PackageLevel.class);
    }

    @Test
    public void whenMaxStringPassThenReturnMaxInstance() throws UnknownParameterException {
        final ClusteringLevel level = ClusteringLevelFactory.getClusteringLevel("file");
        assertSame(level.getClass(), FileLevel.class);
    }

    @Test(expected = UnknownParameterException.class)
    public void whenInvalidStringThenTrowExceptionTest() throws UnknownParameterException {
        ClusteringLevelFactory.getClusteringLevel("pavlina");
    }

}