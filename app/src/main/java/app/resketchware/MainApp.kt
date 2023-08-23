package app.resketchware

import android.app.Application
import app.resketchware.utils.LogWriter

class MainApp : Application() {
    private var logWriter: LogWriter? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        logWriter = LogWriter(applicationContext).also { it.start() }
    }

    override fun onTerminate() {
        super.onTerminate()
        logWriter?.stop()
    }

    companion object {
        lateinit var instance: MainApp
    }
}
