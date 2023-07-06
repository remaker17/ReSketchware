package app.resketchware;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import app.resketchware.ui.activities.CrashLogActivity;

public class App extends Application {

    @SuppressLint("StaticFieldLeak") // it is not a leak
    private static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        applicationContext = getApplicationContext();
        setupUncaughtExceptionHandler();
        super.onCreate();
    }

    private void setupUncaughtExceptionHandler() {
        Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            Intent intent = new Intent(applicationContext, CrashLogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(CrashLogActivity.ERROR_EXTRA, Log.getStackTraceString(throwable));
            PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, 11111, intent,
                    PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                    .set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
            Process.killProcess(Process.myPid());
            System.exit(1);
            if (oldHandler != null) {
                oldHandler.uncaughtException(thread, throwable);
            }
        });
    }
}