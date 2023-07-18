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
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private ProjectBuilder.OnResultListener mOnResultListener;

    public static class CompilerBinder extends Binder {
        private final WeakReference<CompilerService> mServiceReference;

        public CompilerBinder(CompilerService service) {
            mServiceReference = new WeakReference<>(service);
        }

        public CompilerService getCompilerService() {
            return mServiceReference.get();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setOnResultListener(ProjectBuilder.OnResultListener onResultListener) {
        mOnResultListener = onResultListener;
    }

    public void compile(Project project, ProgressListener listener) {
        if (project == null) {
            if (mOnResultListener != null) {
                mMainHandler.post(() -> mOnResultListener.onComplete(false, "Unable to compile project"));
            }
            return;
        }

        boolean success = true;

        try {
            ProjectBuilder projectBuilder = new ProjectBuilder(project, listener);
            projectBuilder.build();
        } catch (Throwable e) {
            String message = Log.getStackTraceString(e);
            mMainHandler.post(() -> mOnResultListener.onComplete(false, message));
            success = false;
        }

        report(success);
    }

    private void report(boolean success) {
        if (success) {
            mMainHandler.post(() -> mOnResultListener.onComplete(true, "Success"));
        }

        stopSelf();
        stopForeground(true);
    }
}