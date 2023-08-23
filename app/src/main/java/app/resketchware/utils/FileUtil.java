package app.resketchware.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import app.resketchware.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kotlin.io.FilesKt;

public class FileUtil {

    private FileUtil() {}

    public static byte[] readBytes(File file) {
        return FilesKt.readBytes(file);
    }

    public static String readFile(File file) {
        return FilesKt.readText(file, StandardCharsets.UTF_8);
    }

    public static String readFile(String path) {
        return readFile(new File(path));
    }

    public static void saveFile(String name, String content) {
        try {
            FileWriter writer = new FileWriter(name);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectory(String path) {
        createDirectory(new File(path), false);
    }

    public static void createDirectory(File file) {
        createDirectory(file, false);
    }

    public static void createDirectory(String path, boolean deleteIfExists) {
        createDirectory(new File(path), deleteIfExists);
    }

    public static void createDirectory(File file, boolean deleteIfExists) {
        if (deleteIfExists && isExists(file)) {
            deleteFile(file);
        }
        file.mkdirs();
    }

    public static void deleteFile(String path) {
        deleteFile(new File(path));
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileArr = file.listFiles();

        if (fileArr != null) {
            for (File subFile : fileArr) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile);
                }

                if (subFile.isFile()) {
                    subFile.delete();
                }
            }
        }

        file.delete();
    }

    public static void listDir(String path, ArrayList<String> list) {
        File[] listFiles;
        File dir = new File(path);
        if (dir.exists() && !dir.isFile() && (listFiles = dir.listFiles()) != null && listFiles.length > 0 && list != null) {
            list.clear();
            for (File file : listFiles) {
                list.add(file.getAbsolutePath());
            }
        }
    }

    public static ArrayList<String> listFiles(String dir, String extension) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();
        listDir(dir, files);
        for (String str : files) {
            if (str.endsWith(extension) && new File(str).isFile()) {
                list.add(str);
            }
        }
        return list;
    }

    public static List<File> listFilesRecursively(File directory, String optionalFilenameExtension) {
        List<File> files = new LinkedList<>();

        File[] directoryFiles = directory.listFiles();
        if (directoryFiles != null) {
            for (File file : directoryFiles) {
                if (file.isFile()) {
                    if (optionalFilenameExtension != null && file.getName().endsWith(optionalFilenameExtension)) {
                        files.add(file);
                    }
                } else {
                    files.addAll(listFilesRecursively(file, optionalFilenameExtension));
                }
            }
        }

        return files;
    }

    public static boolean isExists(String path) {
        return isExists(new File(path));
    }

    public static boolean isExists(File file) {
        return file.exists();
    }

    public static void copyFromAssets(String assetPath, String targetPath) throws IOException {
        copyFromAssets(App.getContext(), assetPath, targetPath);
    }

    public static void copyFromAssets(Context context, String assetPath, String targetPath) throws IOException {
        AssetManager assetManager = context.getAssets();
        createTargetDirectoryIfNotExists(targetPath);

        try (InputStream inputStream = assetManager.open(assetPath);
             FileOutputStream fileOutputStream = new FileOutputStream(targetPath)) {

            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new IOException("Failed to copy asset file " + assetPath + " to " + targetPath + ".", e);
        }
    }

    private static void createTargetDirectoryIfNotExists(String targetPath) {
        int index = targetPath.lastIndexOf(File.separator);
        if (index > 0) {
            String dir = targetPath.substring(0, index);
            new File(dir).mkdirs();
        }
    }

    public static boolean hasFileChanged(String assetPath, String targetFilePath) {
        return hasFileChanged(App.getContext(), assetPath, targetFilePath);
    }

    public static boolean hasFileChanged(Context context, String assetPath, String targetFilePath) {
        File targetFile = new File(targetFilePath);
        long lengthOfAssetFile;
        try {
            lengthOfAssetFile = getAssetFileLength(context, assetPath);
        } catch (IOException io) {
            lengthOfAssetFile = 0;
        }

        if (targetFile.exists() && targetFile.length() == lengthOfAssetFile) {
            return false;
        } else {
            deleteFile(targetFilePath);
            return true;
        }
    }

    private static long getAssetFileLength(Context context, String assetPath) throws IOException {
        try (InputStream inputStream = context.getAssets().open(assetPath)) {
            return inputStream.available();
        } catch (IOException e) {
            throw new IOException("Failed to get the length of asset file " + assetPath + ".", e);
        }
    }
}