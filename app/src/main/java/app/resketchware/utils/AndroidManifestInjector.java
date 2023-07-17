package app.resketchware.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AndroidManifestInjector {

    public static String mHolder(String m, String projectId) {
        ArrayList<String> manifestLines = new ArrayList<>(Arrays.asList(m.split("\n")));
        StringBuilder returnValue = new StringBuilder();

        for (String manifestLine : manifestLines) {
            returnValue.append("\n");
            returnValue.append(manifestLine);
        }

        return returnValue.toString();
    }
}