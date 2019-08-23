package extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ModuleReader;

public class ModuleExtractor {

    private final ModuleReader moduleReader;

    public ModuleExtractor(final ModuleReader moduleReader) {
        this.moduleReader = moduleReader;
    }

    public List<String> extract(final String[] args, String repo) {
        final List<String> clustersPaths = new ArrayList<>();
        final List<String> all = args.length > 3 ? new ArrayList<>(Arrays.asList(args).subList(3, args.length)) : new ArrayList<>();
        for (final String s : all) {
            clustersPaths.addAll(moduleReader.readFromPackageName(s, repo));
        }
        return  clustersPaths;
    }
}
