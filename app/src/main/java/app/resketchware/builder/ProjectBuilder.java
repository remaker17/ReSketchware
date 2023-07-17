package app.resketchware.builder;

import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.builder.tasks.Aapt2Task;
import app.resketchware.builder.tasks.ExtractBuiltInLibrariesTask;
import app.resketchware.builder.tasks.D8Task;
import app.resketchware.builder.tasks.JavaTask;
import app.resketchware.ui.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder extends BuilderImpl {

    public interface OnResultListener {
        void onComplete(boolean success, String message);
    }

    public ProjectBuilder(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public List<Task> getTasks() {
        Project project = getProject();
        ProgressListener listener = getProgressListener();

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Aapt2Task(project, listener));
        tasks.add(new ExtractBuiltInLibrariesTask(project, listener));
        tasks.add(new JavaTask(project, listener));
        tasks.add(new D8Task(project, listener));
        return tasks;
    }
}