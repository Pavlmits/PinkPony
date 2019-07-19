import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

class GitCreator {

    private GitCreator() {
    }

    static Git createLocalGitInstance(final String path) throws IOException {
        return new Git(openJGitLocalRepository(path));
    }

    static Git createRemoteGitInstance(final String url, final String localPath) throws GitAPIException {
        return Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(localPath))
                .setCloneAllBranches(true)
                .call();
    }

    private static Repository openJGitLocalRepository(String path) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(path))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }
}
