package info.hellovass.gallerydownloader.sample.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Environment
import android.view.View
import com.trello.rxlifecycle2.LifecycleProvider
import info.hellovass.gallerydownloader.library.DefaultOkHttpFactory
import info.hellovass.gallerydownloader.library.MediaDownloader
import info.hellovass.gallerydownloader.library.SingleLiveEvent
import info.hellovass.gallerydownloader.sample.m.SampleRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class SampleVM(provider: LifecycleProvider<Lifecycle.Event>) : BaseVM(provider) {

    // 进度
    private val _progress = MutableLiveData<Int>()

    // toast
    private val _toastMsg = SingleLiveEvent<String>()

    // sanckbar
    private val _snackbarMsg = SingleLiveEvent<String>()

    private val repo: SampleRepo by lazy {
        SampleRepo()
    }

    fun progress(): LiveData<Int> = _progress

    fun toastMsg(): SingleLiveEvent<String> = _toastMsg

    fun snackbarMsg(): SingleLiveEvent<String> = _snackbarMsg

    fun onStartDownload(view: View) {

        // 保存目录
        val savePath = "${Environment.getExternalStorageDirectory()}${File.separator}Pero${File.separator}雾聚dalao"

        MediaDownloader.Builder()
                .urls(repo.gallery())
                .okHttpFactory(DefaultOkHttpFactory.getInstance(savePath))
                .build()
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _progress.postValue(it) }, { _toastMsg.postValue(it.message) }) { _snackbarMsg.postValue(savePath) }
    }
}
