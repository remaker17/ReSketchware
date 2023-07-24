package app.resketchware.builder.tasks;

import com.android.apksig.ApkSigner;
import com.android.apksig.SignUtils;
import com.android.apksig.apk.ApkFormatException;
import com.google.common.collect.ImmutableList;

import app.resketchware.App;
import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.Decompress;
import app.resketchware.utils.ContextUtil;

import java.io.File;
import java.io.IOException;

public class SignTask extends Task {

    private File mInputApk;
    private File mOutputApk;

    private File testKey;
    private File testCert;

    private ApkSigner.SignerConfig signerConfig;

    public SignTask(Project project, ProgressListener listener) {
        super(project, listener);
    }

    @Override
    public String getName() {
        return ContextUtil.getString(R.string.compiler_sign_message);
    }

    @Override
    public void prepare() throws IOException {
        mInputApk = project.getUnsignedUnalignedApkDirectory();
        mOutputApk = project.getFinalToInstallApkDirectory();
        testKey = new File(getTestKeyFile());
        testCert = new File(getTestCertFile());
    }

    @Override
    public void run() throws IOException, CompilationFailedException {
        try {
            signerConfig = SignUtils.getSignerConfig(testKey.getAbsolutePath(), testCert.getAbsolutePath());

            ApkSigner signer = new ApkSigner.Builder(ImmutableList.of(signerConfig))
                    .setInputApk(mInputApk)
                    .setOutputApk(mOutputApk)
                    .setMinSdkVersion(projectSettings.getMinSdkVersion())
                    .build();

            signer.sign();
        } catch (ApkFormatException e) {
            throw new CompilationFailedException(e.toString());
        } catch (Exception e) {
            throw new CompilationFailedException(e);
        }
    }

    private String getTestKeyFile() {
        File check = new File(App.getContext().getFilesDir() + "/temp/testkey.pk8");
        if (check.exists()) {
            return check.getAbsolutePath();
        }
        Decompress.unzipFromAssets("testkey.pk8.zip", check.getParentFile().getAbsolutePath());
        return check.getAbsolutePath();
    }

    private String getTestCertFile() {
        File check = new File(App.getContext().getFilesDir() + "/temp/testkey.x509.pem");
        if (check.exists()) {
            return check.getAbsolutePath();
        }
        Decompress.unzipFromAssets("testkey.x509.pem.zip", check.getParentFile().getAbsolutePath());
        return check.getAbsolutePath();
    }
}