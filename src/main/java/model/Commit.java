package model;

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
public class Commit {

    private String name;

    private PersonIdent author;

    private PersonIdent committer;

    private List<String> paths;

    private String fullMessage;

    private Commit[] parents;

}
