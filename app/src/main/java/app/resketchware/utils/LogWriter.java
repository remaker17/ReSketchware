package app.resketchware.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogWriter {
  private static final String TAG = "LogWriter";
  private static final String LOG_FILE_NAME = "log.txt";
  private static final String LOG_DIR_NAME = "logs";

  private final Context mContext;
  private final File mLogFile;
  private Process mLogcatProcess;
  private BufferedReader mLogcatReader;
  private FileWriter mLogFileWriter;

  public LogWriter(Context context) {
    mContext = context;
    File logDir = new File(mContext.getExternalFilesDir(null), LOG_DIR_NAME);
    if (!logDir.exists()) {
      if (!logDir.mkdirs()) {
        Log.e(TAG, "Failed to create log directory");
      }
    }
    mLogFile = new File(logDir, LOG_FILE_NAME);
  }

  public void start() {
    Log.d(TAG, "Starting log writer...");
    try {
      mLogFileWriter = new FileWriter(mLogFile, true);
      mLogcatProcess = Runtime.getRuntime().exec("logcat -v time -s *:V");
      mLogcatReader = new BufferedReader(new InputStreamReader(mLogcatProcess.getInputStream()));

      Thread logcatThread = new Thread(() -> {
        String line;
        while (true) {
          try {
            line = mLogcatReader.readLine();
            if (line != null) {
              writeLog(line);
            }
          } catch (IOException e) {
            Log.e(TAG, "Failed to read logcat output: " + e.getMessage());
            break;
          }
        }
      });
      logcatThread.start();
    } catch (IOException e) {
      Log.e(TAG, "Failed to start logcat process: " + e.getMessage());
    }
    Log.d(TAG, "Log writer started.");
  }

  public void stop() {
    Log.d(TAG, "Stopping log writer...");
    try {
      mLogcatProcess.destroy();
      mLogcatReader.close();
      mLogFileWriter.close();
    } catch (IOException e) {
      Log.e(TAG, "Failed to stop logcat process: " + e.getMessage());
    }
    Log.d(TAG, "Log writer stopped.");
  }

  private void writeLog(String message) {
    String logMessage = message + "\n";
    try {
      mLogFileWriter.append(logMessage);
      mLogFileWriter.flush();
    } catch (IOException e) {
      Log.e(TAG, "Failed to write log message to file: " + e.getMessage());
    }
  }
}
