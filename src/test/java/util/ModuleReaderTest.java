package util;

import org.junit.Ignore;
import org.junit.Test;

public class ModuleReaderTest {

    private ModuleReader moduleReader = new ModuleReader();


    @Test(expected = NullPointerException.class)
    public void readWhenNoPackageNoRepo() {
        moduleReader.readFromPackageName("", "");
    }

    @Ignore
    @Test
    public void readTest() {
        moduleReader.readFromPackageName("pa", "repo");
    }

}