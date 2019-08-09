package util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FilesPrefixListChecker {

    public static boolean isInTheList(final List<String> files, final List<String> prefixList) {
        for (final String file : files) {
            if (StringUtils.startsWithAny(file, prefixList.toArray(new String[0]))) {
                return true;
            }
        }
        return false;
    }
}
