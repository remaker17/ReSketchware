package app.resketchware.builder;

import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.builder.tasks.Aapt2Task;
import app.resketchware.builder.tasks.ExtractAapt2Task;
import app.resketchware.builder.tasks.ExtractBuiltInLibrariesTask;
import app.resketchware.builder.tasks.D8Task;
import app.resketchware.builder.tasks.JavaTask;
import app.resketchware.builder.tasks.PackageTask;
import app.resketchware.builder.tasks.SetupTask;
import app.resketchware.builder.tasks.SignTask;
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
        tasks.add(new SetupTask(project, listener));
        tasks.add(new ExtractAapt2Task(project, listener));
        tasks.add(new ExtractBuiltInLibrariesTask(project, listener));
        tasks.add(new Aapt2Task(project, listener));
        tasks.add(new JavaTask(project, listener));
        tasks.add(new D8Task(project, listener));
        tasks.add(new PackageTask(project, listener));
        tasks.add(new SignTask(project, listener));
        return tasks;
    }
}