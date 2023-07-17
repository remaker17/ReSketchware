package app.resketchware.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import app.resketchware.builder.ProjectBuilder;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import java.lang.ref.WeakReference;

public class CompilerService extends Service {

    private final CompilerBinder mBinder = new CompilerBinder(this);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ProjectBuilder.OnResultListener onResultListener;
    private ProgressListener listener;
    private Project project;

    public static class CompilerBinder extends Binder {
        private final WeakReference<CompilerService> serviceReference;

        public CompilerBinder(CompilerService service) {
            serviceReference = new WeakReference<>(service);
        }

        public CompilerService getCompilerService() {
            return serviceReference.get();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    public void setOnResultListener(ProjectBuilder.OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void compile(Project project) {
        this.project = project;
        if (project == null) {
            if (onResultListener != null) {
                mainHandler.post(() -> onResultListener.onComplete(false, "Unable to open project"));
            }
            return;
        }
        buildProject(project, listener);
    }

    private void buildProject(Project project, ProgressListener listener) {
        boolean success = true;

        try {
            ProjectBuilder projectBuilder = new ProjectBuilder(project, listener);
            projectBuilder.build();
        } catch (Throwable e) {
            String message = Log.getStackTraceString(e);
            mainHandler.post(() -> onResultListener.onComplete(false, message));
            success = false;
        }

        report(success);
    }

    private void report(boolean success) {
        if (success) {
            mainHandler.post(() -> onResultListener.onComplete(true, "Success"));
        }

        stopSelf();
        stopForeground(true);
    }
}