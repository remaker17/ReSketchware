package app.resketchware.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.viewmodels.CompilerViewModel;

public class CompilerServiceConnection implements ServiceConnection {

    private boolean mCompiling;

    private final CompilerViewModel mCompilerViewModel;
    private CompilerService mService;

    public CompilerServiceConnection(@NonNull CompilerViewModel viewModel) {
        mCompilerViewModel = viewModel;
    }

    public boolean isCompiling() {
        return mCompiling;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        mService = ((CompilerService.CompilerBinder) binder).getCompilerService();
        if (mService == null) {
            mCompilerViewModel.setMessage("CompilerService is null");
            return;
        }

        mCompiling = true;
        mService.setOnResultListener((success, message) -> {
            Log.d("CompilerServiceConnection", message);
            mCompilerViewModel.setMessage(message);
        });
        mService.compile(mCompilerViewModel.getProject().getValue(), message -> {
            mCompilerViewModel.setMessage(message);
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
        // mCompilerViewModel.setMessage(null);
        // mCompilerViewModel.setCompiling(false);
        mCompiling = false;
    }
}