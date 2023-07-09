package app.resketchware.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import kotlin.io.FilesKt;

public class FileUtil {

    public static String readFile(File file) {
        return FilesKt.readText(file, StandardCharsets.UTF_8);
    }
}