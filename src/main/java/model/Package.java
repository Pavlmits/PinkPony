package model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Package {

    private String name;

    //private String folderName;

    private List<String> files;


    @Override
    public String toString() {
        return /*folderName + "/" +*/ name;
    }
}