package model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Commit {

    private long id;

    private String author;

    private List<String> paths;

    private String message;

}
