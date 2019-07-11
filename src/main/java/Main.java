import java.io.IOException;
import java.util.List;
import java.util.Map;

import extractors.CommitExtractor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {
        final String path = "https://github.com/Pavlmits/PinkPony/";
        final Git git = GitCreator.createLocalGitInstance(path);
        final List<RevCommit> commitsList = CommitExtractor.extractWithOutFiles(git);
        final Map<RevCommit, List<DiffEntry>> commitMap = CommitExtractor.extractWithFiles(git);
        System.out.println("ok");

//        final ObjectId tree = commitsList.get(0).getTree();
//
//        TreeWalk treeWalk = new TreeWalk(extractors.CommitExtractor.openJGitRepository(path));
//        List<String> filesList = new ArrayList<>();
//        treeWalk.addTree(tree);
//        treeWalk.setRecursive(false);
//        while (treeWalk.next()) {
//            if (treeWalk.isSubtree()) {
//                treeWalk.enterSubtree();
//            } else {
//                filesList.add(treeWalk.getPathString());
//            }
//        }
//        System.out.println(filesList.size());
//        final Graph<String, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);
//        for(String file: filesList){
//            graph.addVertex(file);
//        }
    }


}
