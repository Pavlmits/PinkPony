package model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.jgit.lib.PersonIdent;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Commit implements Serializable {

    private String name;

    private PersonIdent author;

    private PersonIdent committer;

    private List<String> paths;

    private List<String> oldPaths;

    private String fullMessage;

    private boolean isMerged;

    public Commit(final String name, final List<String> paths, final List<String> oldPaths) {
        this.name = name;
        this.paths = paths;
        this.oldPaths = oldPaths;
    }
}
