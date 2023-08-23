package app.resketchware.builder.tasks;

import static android.system.OsConstants.S_IRUSR;
import static android.system.OsConstants.S_IWUSR;
import static android.system.OsConstants.S_IXUSR;

import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import app.resketchware.App;
import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.Decompress;
import app.resketchware.utils.FileUtil;

import java.io.File;
import java.io.IOException;

public class ExtractAapt2Task extends Task {

    private File aaptBinary;

    public ExtractAapt2Task(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_extract_aapt2_message);
    }

    @Override
    public void prepare() throws IOException {
        aaptBinary = new File(App.getContext().getCacheDir(), "aapt2");
    }

    @Override
    public void run() throws IOException, CompilationFailedException {
        String aapt2PathInAssets = "aapt" + File.separator;
        if (getAbi().toLowerCase().contains("x86")) {
            aapt2PathInAssets += "aapt2-x86";
        } else {
            aapt2PathInAssets += "aapt2-arm";
        }
        try {
            if (FileUtil.hasFileChanged(aapt2PathInAssets, aaptBinary.getAbsolutePath())) {
                Decompress.unzipFromAssets(aapt2PathInAssets, aaptBinary.getParentFile().getAbsolutePath());
                Os.chmod(aaptBinary.getAbsolutePath(), S_IRUSR | S_IWUSR | S_IXUSR);
            }
        } catch (ErrnoException e) {
            Log.d("ExtractAapt2Task", "Failed to extract AAPT2 binary");
            throw new IOException(e);
        } catch (Exception e) {
            Log.d("ExtractAapt2Task", "Failed to extract AAPT2 binary");
            throw new IOException(e);
        }
    }

    private String getAbi() {
        if (Build.VERSION.SDK_INT >= 21) {
            String[] supportedAbis = Build.SUPPORTED_ABIS;
            if (supportedAbis != null) {
                return supportedAbis[0];
            }
        }
        return Build.CPU_ABI;
    }
}