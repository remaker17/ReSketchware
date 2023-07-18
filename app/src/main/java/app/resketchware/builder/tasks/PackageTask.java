package app.resketchware.builder.tasks;

import android.net.Uri;

import com.android.sdklib.build.ApkBuilder;
import com.android.sdklib.build.ApkCreationException;
import com.android.sdklib.build.DuplicateFileException;
import com.android.sdklib.build.SealedApkException;

import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.managers.BuiltInLibraryManager;
import app.resketchware.ui.models.BuiltInLibraryModel;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.BuiltInLibraries;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageTask extends Task {

    private final List<File> dexFiles = new ArrayList<>();
    private static List<File> dexesToAddButNotMerge = new ArrayList<>();
    private final List<File> libraries = new ArrayList<>();

    public PackageTask(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_package_message);
    }

    @Override
    public void prepare() throws IOException {}

    @Override
    public void run() throws IOException, CompilationFailedException {
        String firstDexPath = dexesToAddButNotMerge.isEmpty() ? project.getClassesDexDirectory().getAbsolutePath() : dexesToAddButNotMerge.remove(0).getAbsolutePath();
        try {
            ApkBuilder builder = new ApkBuilder(
                    new File(project.getUnsignedUnalignedApkDirectory().getAbsolutePath()),
                    new File(project.getResourcesApkDirectory().getAbsolutePath()),
                    new File(firstDexPath),
                    null, null,
                    System.out);

            for (BuiltInLibraryModel library : BuiltInLibraryManager.getLibraries()) {
                builder.addResourcesFromJar(BuiltInLibraries.getLibraryClassesJarPath(library.getName()));
            }

            if (dexesToAddButNotMerge.isEmpty()) {
                List<String> dexFiles = FileUtil.listFiles(project.getBinDirectory().getAbsolutePath(), "dex");

                for (String dexFile : dexFiles) {
                    if (!Uri.fromFile(new File(dexFile)).getLastPathSegment().equals("classes.dex")) {
                        builder.addFile(new File(dexFile), Uri.parse(dexFile).getLastPathSegment());
                    }
                }
            } else {
                int dexNumber = 2;

                for (File dexFile : dexesToAddButNotMerge) {
                    builder.addFile(dexFile, "classes" + dexNumber + ".dex");
                    dexNumber++;
                }
            }

            builder.setDebugMode(false);
            builder.sealApk();
        } catch (ApkCreationException | SealedApkException e) {
            throw new CompilationFailedException(e.getMessage());
        } catch (DuplicateFileException e) {
            String message = "Duplicate files from two libraries detected \r\n";
            message += "File1: " + e.getFile1() + " \r\n";
            message += "File2: " + e.getFile2() + " \r\n";
            message += "Archive path: " + e.getArchivePath();
            throw new CompilationFailedException(message);
        }
    }

    public static void addDexesToAddButNotMerge(List<File> dexes) {
        dexesToAddButNotMerge = dexes;
    }
}