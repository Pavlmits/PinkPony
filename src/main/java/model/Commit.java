package model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Commit {

    private long id;

    private String author;

    private List<String> files;

    private String message;

    private LocalDateTime date;
}
