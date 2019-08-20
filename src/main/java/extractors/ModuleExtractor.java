package extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ModuleReader;

public class ModuleExtractor {

    public static List<String> extract(final String[] args, String repo) {
        final List<String> clustersPaths = new ArrayList<>();
        final List<String> all = args.length > 3 && !args[3].equals("all") ? new ArrayList<>(Arrays.asList(args).subList(3, args.length)) : new ArrayList<>();
        for (final String s : all) {
            clustersPaths.addAll(ModuleReader.readFromPackageName(s, repo));
        }
        return  clustersPaths;
    }
}
