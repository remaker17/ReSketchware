package app.resketchware.builder.tasks;

import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.ContextUtil;

import java.io.IOException;

public class SetupTask extends Task {

    public SetupTask(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_delete_temporary_files_message);
    }

    @Override
    public void prepare() throws IOException {}

    @Override
    public void run() throws IOException, CompilationFailedException {
        project.deleteTemporaryFiles();
        project.createNeededDirectories();
        project.extractResFromAssets();
        project.generateProjectFiles();
    }
}