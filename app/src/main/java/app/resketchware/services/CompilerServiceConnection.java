package app.resketchware.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.viewmodels.CompilerViewModel;

public class CompilerServiceConnection implements ServiceConnection {

    private final CompilerViewModel compilerViewModel;

    private CompilerService service;
    private boolean isCompiling;

    public CompilerServiceConnection(@NonNull CompilerViewModel compilerViewModel) {
        this.compilerViewModel = compilerViewModel;
    }

    public boolean isCompiling() {
        return isCompiling;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((CompilerService.CompilerBinder) binder).getCompilerService();
        if (service == null) {
            compilerViewModel.setMessage("CompilerService is null");
            return;
        }

        isCompiling = true;
        service.setListener(ProgressListener.wrap(compilerViewModel));
        service.setOnResultListener((success, message) -> {
            Log.d("CompilerServiceConnection", message);
            compilerViewModel.setMessage(message);
            // temporary set to true
            compilerViewModel.setCompiling(true);
        });
        service.compile(compilerViewModel.getProject().getValue());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
        // compilerViewModel.setMessage(null);
        // compilerViewModel.setCompiling(false);
        isCompiling = false;
    }
}