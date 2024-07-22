package app.resketchware.builder.tasks;

import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.managers.BuiltInLibraryManager;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.BuiltInLibraries;
import app.resketchware.utils.ContextUtil;

import java.io.IOException;

public class ExtractBuiltInLibrariesTask extends Task {
  public ExtractBuiltInLibrariesTask(Project project, ProgressListener listener) {
    super(project, listener);
  }

  @Override
  public String getName() {
    return ContextUtil.getString(R.string.compiler_extract_libraries_message);
  }

  @Override
  public void prepare() throws IOException {}

  @Override
  public void run() throws IOException, CompilationFailedException {
    BuiltInLibraries.extractCompileAssets(listener);

    // Temporary add these libraries
    BuiltInLibraryManager.addLibrary(BuiltInLibraries.ANDROIDX_APPCOMPAT);
    BuiltInLibraryManager.addLibrary(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT);
    BuiltInLibraryManager.addLibrary(BuiltInLibraries.MATERIAL);
  }
}
