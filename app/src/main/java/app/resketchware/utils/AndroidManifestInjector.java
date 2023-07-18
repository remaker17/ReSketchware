package app.resketchware.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndroidManifestInjector {
    private AndroidManifestInjector() {}

    public static String mHolder(String manifest) {
        List<String> manifestLines = new ArrayList<>(Arrays.asList(manifest.split("\n")));
        StringBuilder result = new StringBuilder();

        for (String line : manifestLines) {
            if (result.length() == 0) {
                result.append(line);
                continue;
            }
            result.append('\n');
            result.append(line);
        }

        return result.toString();
    }
}