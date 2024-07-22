package app.resketchware.builder;

import androidx.annotation.NonNull;

import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BuilderImpl implements Builder {
  private final Project mProject;
  private final ProgressListener mListener;
  private final List<Task> mTaskRan;

  public BuilderImpl(Project project, ProgressListener listener) {
    mProject = project;
    mListener = listener;
    mTaskRan = new ArrayList<>();
  }

  @NonNull
  @Override
  public Project getProject() {
    return mProject;
  }

  @Override
  public final void build() throws CompilationFailedException, IOException {
    mTaskRan.clear();
    List<Task> tasks = getTasks();
    for (int i = 0, tasksSize = tasks.size(); i < tasksSize; i++) {
      Task task = tasks.get(i);
      mListener.onProgress(task.getName());
      try {
        task.prepare();
        task.run();
      } catch (Throwable th) {
        if (th instanceof OutOfMemoryError) {
          tasks.clear();
          mTaskRan.clear();
          throw new CompilationFailedException("Builder ran out of memory", th);
        }
        task.clean();
        mTaskRan.forEach(Task::clean);
        throw th;
      }
      mTaskRan.add(task);
    }
    mTaskRan.forEach(Task::clean);
  }

  @Override
  public ProgressListener getProgressListener() {
    return mListener;
  }

  public abstract List<Task> getTasks();
}
