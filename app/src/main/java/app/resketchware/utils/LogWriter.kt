package app.resketchware.utils

import android.content.Context
import android.util.Log
import java.io.*

class LogWriter(private val context: Context) {
    private val logFile: File

    private var logcatProcess: Process? = null
    private var logcatReader: BufferedReader? = null
    private var logFileWriter: FileWriter? = null

    companion object {
        private const val TAG = "LogWriter"
        private const val LOG_FILE_NAME = "log.txt"
        private const val LOG_DIR_NAME = "logs"
    }

    init {
        val logDir = File(context.getExternalFilesDir(null), LOG_DIR_NAME)
        if (!logDir.exists() && !logDir.mkdirs()) {
            Log.e(TAG, "Failed to create log directory")
        }
        logFile = File(logDir, LOG_FILE_NAME)
    }

    fun start() {
        Log.d(TAG, "Starting log writer...")
        try {
            logFileWriter = FileWriter(logFile, true)
            logcatProcess = Runtime.getRuntime().exec("logcat -v time -s *:V")
            logcatReader = BufferedReader(InputStreamReader(logcatProcess!!.inputStream))

            val logcatThread = Thread {
                while (true) {
                    try {
                        val line = logcatReader!!.readLine()
                        if (line != null) {
                            writeLog(line)
                        }
                    } catch (e: IOException) {
                        Log.e(TAG, "Failed to read logcat output: ${e.message}")
                        break
                    }
                }
            }
            logcatThread.start()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to start logcat process: ${e.message}")
        }

        Log.d(TAG, "Log writer started.")
    }

    fun stop() {
        Log.d(TAG, "Stopping log writer...")
        try {
            logcatProcess?.destroy()
            logcatReader?.close()
            logFileWriter?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to stop logcat process: ${e.message}")
        }
        Log.d(TAG, "Log writer stopped.")
    }

    private fun writeLog(message: String) {
        val logMessage = "$message\n"
        try {
            logFileWriter?.append(logMessage)
            logFileWriter?.flush()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to write log message to file: ${e.message}")
        }
    }
}
