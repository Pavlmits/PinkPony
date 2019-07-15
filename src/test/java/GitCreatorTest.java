import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import org.junit.Before;
import org.junit.Test;

public class GitCreatorTest {

    private String localPath;

    private File remotePath;

    @Before
    public void setup(){
        localPath = new File("").getAbsolutePath();
        remotePath = new File("C:\\project");

    }

    @Test
    public void createLocalGitInstance() throws IOException, GitAPIException {
        final Git git = GitCreator.createLocalGitInstance(localPath +"\\.git");
        assertTrue(!git.branchList().setListMode( ListBranchCommand.ListMode.ALL ).call().isEmpty());
    }

    @Test
    public void createRemoteGitInstance() throws GitAPIException, IOException {
        final Git  git = GitCreator.createRemoteGitInstance("https://github.com/Pavlmits/PinkPony", remotePath.getAbsolutePath());
        assertTrue(!git.branchList().setListMode( ListBranchCommand.ListMode.ALL ).call().isEmpty());
        git.close();
        FileUtils.forceDelete(remotePath.getAbsoluteFile());
    }


}