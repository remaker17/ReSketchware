package app.resketchware;

import android.app.Application;
import android.content.Context;

import app.resketchware.ui.activities.CrashLogActivity;

public class App extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}