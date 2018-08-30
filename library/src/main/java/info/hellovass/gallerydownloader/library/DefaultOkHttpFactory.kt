package info.hellovass.gallerydownloader.library

import info.hellovass.gallerydownloader.library.DigestUtils.md5
import io.reactivex.Observable
import io.reactivex.ObservableSource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

class DefaultOkHttpFactory private constructor(savePath: String) : OkHttpFactory {

    companion object {

        @Volatile
        private var INSTANCE: OkHttpFactory? = null

        fun getInstance(savePath: String): OkHttpFactory =
                INSTANCE ?: synchronized(DefaultOkHttpFactory::class.java) {
                    INSTANCE ?: DefaultOkHttpFactory(savePath).also { INSTANCE = it }
                }
    }

    private val okHttpClient: OkHttpClient

    private val presisted: Presisted

    init {
        okHttpClient = createOkHttpClient()
        presisted = DefaultPresisted(savePath)
    }

    override fun download(url: String): ObservableSource<String> {

        return Observable.create<String> { emitter ->
            try {
                // build request
                val request = Request.Builder()
                        .url(url)
                        .get()
                        .build()
                // execute request, get reponse sync
                val response = okHttpClient.newCall(request)
                        .execute()
                // get the response body
                val responseBody: ResponseBody? = response.body()
                // null safe
                responseBody?.let { it ->
                    // write to storage
                    presisted.write(it.byteStream(), md5(url))
                    // succeed
                    emitter.onNext("save to storage succeed")
                    emitter.onComplete()
                }
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
    }

    private fun createOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
    }
}