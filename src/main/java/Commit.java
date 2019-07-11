import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

//@AllArgsConstructor
//@Getter
public class Commit {

    private long id;

    private String author;

    private List<String> files;

    private String message;

    private LocalDateTime date;

    public Commit(final long id, final String author, final List<String> files,
                  final String message, final LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.files = files;
        this.message = message;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getFiles() {
        return files;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
