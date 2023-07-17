package app.resketchware.builder.tasks;

import static android.system.OsConstants.S_IRUSR;
import static android.system.OsConstants.S_IWUSR;
import static android.system.OsConstants.S_IXUSR;

import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import app.resketchware.App;
import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.BinaryExecutor;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.FileUtil;
import app.resketchware.utils.SketchwareUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Aapt2Task extends Task {

    private File aaptBinary;
    private String outputPath;

    public Aapt2Task(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_aapt2_message);
    }

    @Override
    public void prepare() throws IOException {
        aaptBinary = new File(App.getContext().getFilesDir(), "aapt2");
        outputPath = project.getBinDirectory().getAbsolutePath() + File.separator + "res";
        FileUtil.createDirectory(outputPath);
    }

    @Override
    public void run() throws IOException, CompilationFailedException {
        maybeExtractAapt2();
        compileProjectResources();
        compileImportedResources();
        linkResources();
    }

    private void maybeExtractAapt2() throws IOException {
        String aapt2PathInAssets = "aapt" + File.separator;
        if (getAbi().toLowerCase().contains("x86")) {
            aapt2PathInAssets += "aapt2-x86";
        } else {
            aapt2PathInAssets += "aapt2-arm";
        }
        try {
            if (FileUtil.hasFileChanged(aapt2PathInAssets, aaptBinary.getAbsolutePath())) {
                Os.chmod(aaptBinary.getAbsolutePath(), S_IRUSR | S_IWUSR | S_IXUSR);
            }
        } catch (ErrnoException e) {
            Log.d("Aapt2Task", "Failed to extract AAPT2 binary");
            throw new IOException(e);
        } catch (Exception e) {
            Log.d("Aapt2Task", "Failed to extract AAPT2 binary");
            throw new IOException(e);
        }
    }

    private void compileProjectResources() throws CompilationFailedException {
        checkForExist(project.getResDirectory());

        ArrayList<String> commands = new ArrayList<>();
        commands.add(aaptBinary.getAbsolutePath());
        commands.add("compile");
        commands.add("--dir");
        commands.add(project.getResDirectory().getAbsolutePath());
        commands.add("-o");
        commands.add(outputPath + File.separator + "project.zip");

        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(commands);
        if (!executor.execute().isEmpty()) {
            throw new CompilationFailedException(executor.getLog());
        }
    }

    private void compileImportedResources() throws CompilationFailedException {
        if (!FileUtil.isExists(project.getResourceDirectory()) && project.getResourceDirectory().length() == 0) {
            return;
        }

        ArrayList<String> commands = new ArrayList<>();
        commands.add(aaptBinary.getAbsolutePath());
        commands.add("compile");
        commands.add("--dir");
        commands.add(project.getResourceDirectory().getAbsolutePath());
        commands.add("-o");
        commands.add(outputPath + File.separator + "project-imported.zip");

        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(commands);
        if (!executor.execute().isEmpty()) {
            throw new CompilationFailedException(executor.getLog());
        }
    }

    private void linkResources() throws CompilationFailedException {
        listener.post(ContextUtil.getString(R.string.compiler_aapt2_linking_message));

        ArrayList<String> args = new ArrayList<>();
        args.add(aaptBinary.getAbsolutePath());
        args.add("link");
        args.add("--allow-reserved-package-id");
        args.add("--auto-add-overlay");
        args.add("--no-version-vectors");
        args.add("--no-version-transitions");
        args.add("--min-sdk-version");
        args.add(String.valueOf(projectSettings.getMinSdkVersion()));
        args.add("--target-sdk-version");
        args.add(String.valueOf(projectSettings.getTargetSdkVersion()));
        args.add("--version-code");
        // TODO: Make it customizable
        args.add("1");
        args.add("--version-name");
        // TODO: Make it customizable
        args.add("1.0");
        // ---------- ANDROID JAR ----------
        args.add("-I");
        args.add(SketchwareUtil.getBootstrapFile().getAbsolutePath());
        File importedArchive = new File(project.getResourceDirectory().getAbsolutePath(), "project-imported.zip");
        if (FileUtil.isExists(importedArchive)) {
            args.add("-R");
            args.add(importedArchive.getAbsolutePath());
        }

        // ---------- R file ----------
        checkForExist(project.getJavaDirectory());
        args.add("--java");
        args.add(project.getJavaDirectory().getAbsolutePath());

        checkForExist(project.getAndroidManifest());
        args.add("--manifest");
        args.add(project.getAndroidManifest().getAbsolutePath());

        args.add("-o");
        args.add(project.getResourcesApkDirectory().getAbsolutePath());

        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(args);
        if (!executor.execute().isEmpty()) {
            throw new CompilationFailedException(executor.getLog());
        }
    }

    private void checkForExist(File file) throws CompilationFailedException {
        if (!FileUtil.isExists(file)) {
            throw new CompilationFailedException("No such file or directory: " + file.getAbsolutePath());
        }
    }

    private String getAbi() {
        if (Build.VERSION.SDK_INT >= 21) {
            String[] supportedAbis = Build.SUPPORTED_ABIS;
            if (supportedAbis != null) {
                return supportedAbis[0];
            }
        }
        return Build.CPU_ABI;
    }
}