package app.resketchware.builder;

import androidx.annotation.NonNull;

import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;

import java.io.IOException;
import java.util.List;

public interface Builder {

  @NonNull
  Project getProject();

  void build() throws CompilationFailedException, IOException;

  ProgressListener getProgressListener();

  List<Task> getTasks();

}