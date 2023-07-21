package app.resketchware.builder.tasks;

import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.ContextUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;

import kellinwood.security.zipsigner.ZipSigner;

public class SignTask extends Task {

    public SignTask(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_sign_message);
    }

    @Override
    public void prepare() throws IOException {}

    @Override
    public void run() throws IOException, CompilationFailedException {
        try {
            ZipSigner zipSigner = new ZipSigner();
            zipSigner.setKeymode(ZipSigner.KEY_TESTKEY);
            zipSigner.signZip(project.getUnsignedUnalignedApkDirectory(), project.getFinalToInstallApkDirectory());
        } catch (GeneralSecurityException e) {
            throw new CompilationFailedException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new CompilationFailedException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new CompilationFailedException(e.getMessage());
        } catch (InstantiationException e) {
            throw new CompilationFailedException(e.getMessage());
        }
    }
}