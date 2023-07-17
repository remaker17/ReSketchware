package app.resketchware.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import app.resketchware.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import kotlin.io.FilesKt;

public class FileUtil {

    public static byte[] readBytes(File file) {
        return FilesKt.readBytes(file);
    }

    public static String readFile(File file) {
        return FilesKt.readText(file, StandardCharsets.UTF_8);
    }

    public static String readFile(String path) {
        return readFile(new File(path));
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

    public static boolean hasFileChanged(String assetPath, String targetFilePath) throws IOException {
        return hasFileChanged(App.getContext(), assetPath, targetFilePath);
    }

    public static boolean hasFileChanged(Context context, String assetPath, String targetFilePath) throws IOException {
        File targetFile = new File(targetFilePath);
        long lengthOfAssetFile = getAssetFileLength(context, assetPath);

        if (targetFile.exists() && targetFile.length() == lengthOfAssetFile) {
            return false;
        } else {
            Files.deleteIfExists(Paths.get(targetFilePath));
            copyFromAssets(context, assetPath, targetFilePath);
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