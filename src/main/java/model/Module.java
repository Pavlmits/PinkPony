package model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Module {

    private String name;

    private List<String> files;


    @Override
    public String toString() {
        return name;
    }
}
