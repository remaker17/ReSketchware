package app.resketchware.builder;

import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.ProjectSettings;

import java.io.IOException;

public abstract class Task {

    protected final ProjectSettings projectSettings;
    protected final Project project;
    protected final ProgressListener listener;

    public Task(Project project, ProgressListener listener) {
        this.project = project;
        this.listener = listener;

        projectSettings = new ProjectSettings(project.getId());
    }

    /**
     * Called by {@link BuilderImpl} to display the name of the task.
     */
    public abstract String getName();

    /**
     * Called before run() to give the subclass information about the project.
     * @throws IOException if an exception occured during a file operation
     */
    public abstract void prepare() throws IOException;

    /**
     * Called by {@link BuilderImpl} to perform the task needed to do by this subclass.
     * @throws IOException if an exception occured during a file operation
     * @throws CompilationFailedException if an exception occured while the task is running
     */
    public abstract void run() throws IOException, CompilationFailedException;

    protected void clean() {}
}