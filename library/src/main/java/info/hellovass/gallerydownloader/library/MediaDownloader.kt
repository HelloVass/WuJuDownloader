package info.hellovass.gallerydownloader.library

import io.reactivex.Observable

class MediaDownloader(private val urls: List<String>,
                      private val okHttpFactory: OkHttpFactory,
                      private var completedCount: Int) {

    private constructor(builder: Builder) : this(builder.urls, builder.okHttpFactory, 0)

    /**
     * 开始执行任务
     */
    fun execute(): Observable<Int> {
        return Observable.fromIterable(urls)
                .flatMap { url -> okHttpFactory.download(url) }
                .map { calculcateProgress(++completedCount) }
    }

    /**
     * 计算完成进度
     */
    private fun calculcateProgress(completedCount: Int): Int {
        return (completedCount * 100.0F / urls.size).toInt()
    }

    class Builder {

        lateinit var urls: List<String>
            private set

        lateinit var okHttpFactory: OkHttpFactory
            private set

        fun urls(urls: List<String>) = apply {
            this.urls = urls
        }

        fun okHttpFactory(okHttpFactory: OkHttpFactory) = apply {
            this.okHttpFactory = okHttpFactory
        }

        fun build() = MediaDownloader(this)
    }
}