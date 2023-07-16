package app.resketchware.compiler;

import app.resketchware.ui.models.Project;

import java.io.IOException;

public abstract class Compiler {

    private final Project project;

    public Compiler(Project project) {
        this.project = project;
    }

    protected Project getProject() {
        return project;
    }

    /**
     * Called by {@link ProjectBuilder} to display the name of the task.
     */
    public abstract String getName();

    /**
     * Called before run() to give the subclass information about the project.
     * @throws IOException if an exception occured during a file operation
     */
    public abstract void prepare() throws IOException;

    /**
     * Called by {@link ProjectBuilder} to perform the task needed to do by this subclass.
     * @throws IOException if an exception occured during a file operation
     * @throws CompilationFailedException if an exception occured while the task is running
     */
    public abstract void run() throws IOException, CompilationFailedException;
}