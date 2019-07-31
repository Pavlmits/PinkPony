package model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PathFiles {

    private  List<String> newPaths;

    private  List<String> oldPaths;

    public PathFiles(final List<String> newPaths, final List<String> oldPaths) {
        this.newPaths = newPaths;
        this.oldPaths = oldPaths;
    }
}
