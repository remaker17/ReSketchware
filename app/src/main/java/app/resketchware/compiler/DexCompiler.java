package app.resketchware.compiler;

import app.resketchware.ui.models.Project;
import app.resketchware.utils.ContextUtil;

import java.io.IOException;

public class DexCompiler extends Compiler {

    private final Project project;

    public DexCompiler(Project project) {
        super(project);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_dex_message);
    }

    @Override
    public void prepare() throws IOException {}

    @Override
    public void run() throws IOException, CompilationFailedException {}
}