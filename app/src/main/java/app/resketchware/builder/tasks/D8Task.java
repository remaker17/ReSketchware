package app.resketchware.builder.tasks;

import android.os.Build;
import android.util.Log;

import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;

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
import app.resketchware.utils.SketchwareUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class D8Task extends Task {
  private final Collection<Path> libraryFiles = new LinkedList<>();
  private final Collection<Path> programFiles = new LinkedList<>();

  public D8Task(Project project, ProgressListener listener) {
    super(project, listener);
  }

  @Override
  public String getName() {
    return ContextUtil.getString(R.string.compiler_d8_message);
  }

  @Override
  public void prepare() throws IOException {
    FileUtil.createDirectory(new File(project.getBinDirectory().getAbsolutePath(), "dex"));

    for (File file : FileUtil.listFilesRecursively(new File(project.getCompiledClassesDirectory().getAbsolutePath()), ".class")) {
      Log.d("D8Task:prepare", "Adding file to program files: " + file.getAbsolutePath());
      programFiles.add(file.toPath());
    }

    for (String jarPath : SketchwareUtil.getClasspath().split(":")) {
      Log.d("D8Task:prepare", "Adding jar file to library files: " + jarPath);
      libraryFiles.add(Paths.get(jarPath));
    }
  }

  @Override
  public void run() throws IOException, CompilationFailedException {
    if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
      throw new CompilationFailedException("Can't use D8 as API level " + Build.VERSION.SDK_INT + " < 26");
    }

    createDexFilesFromClasses();
    getDexFilesReady();
  }

  private void createDexFilesFromClasses() throws CompilationFailedException {
    try {
      D8.run(D8Command.builder()
          .setMode(CompilationMode.RELEASE)
          .setIntermediate(true)
          .setMinApiLevel(projectSettings.getMinSdkVersion())
          .setOutput(new File(project.getBinDirectory().getAbsolutePath(), "dex").toPath(), OutputMode.DexIndexed)
          .addLibraryFiles(libraryFiles)
          .addProgramFiles(programFiles)
          .build());
    } catch (com.android.tools.r8.CompilationFailedException e) {
      throw new CompilationFailedException(e);
    }
  }

  private void getDexFilesReady() {
    listener.onProgress(ContextUtil.getString(R.string.compiler_merge_dex_message));

    List<File> dexes = new ArrayList<>();

    for (BuiltInLibraryModel library : BuiltInLibraryManager.getLibraries()) {
      dexes.add(BuiltInLibraries.getLibraryDexFile(library.getName()));
    }

    for (String file : FileUtil.listFiles(project.getBinDirectory().getAbsolutePath() + File.separator + "dex", "dex")) {
      dexes.add(new File(file));
    }

    // TODO: do merge if release build or min sdk < 21
    PackageTask.addDexesToAddButNotMerge(dexes);
  }
}
