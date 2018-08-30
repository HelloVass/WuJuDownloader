package info.hellovass.gallerydownloader.sample

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 内存泄漏检测
        initLeakCanary()

        // 日志
        Logger.addLogAdapter(createLogAdapter())
    }

    private fun initLeakCanary() {

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
    }

    private fun createLogAdapter(): LogAdapter {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(2)
                .methodOffset(5)
                .tag("MediaDownloader")
                .build()

        return AndroidLogAdapter(formatStrategy)
    }
}