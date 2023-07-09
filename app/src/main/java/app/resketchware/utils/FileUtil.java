package app.resketchware.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    public static List<File> getFiles(File file) throws Exception {
        List<File> files = getFilesOrEmpty(file);
        if (files.isEmpty()) {
            throw new Exception("Error while getting files");
        }
        return files;
    }

    public static List<File> getFilesOrEmpty(File file) {
        File[] fileList = file.listFiles();
        if (fileList != null) {
            return Arrays.asList(fileList);
        } else {
            return Collections.emptyList();
        }
    }
}