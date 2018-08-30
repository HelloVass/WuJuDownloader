package info.hellovass.gallerydownloader.sample.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.trello.rxlifecycle2.LifecycleProvider
import info.hellovass.gallerydownloader.library.DefaultOkHttpFactory
import info.hellovass.gallerydownloader.library.MediaDownloader
import info.hellovass.gallerydownloader.library.SingleLiveEvent
import info.hellovass.gallerydownloader.sample.m.SampleRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SampleVM(provider: LifecycleProvider<Lifecycle.Event>) : BaseVM(provider) {

    // 进度
    private val progress = MutableLiveData<Int>()

    // toast
    private val toastMsg = SingleLiveEvent<String>()

    // sanckbar
    private val snackbarMsg = SingleLiveEvent<String>()

    private val repo: SampleRepo by lazy {
        SampleRepo()
    }

    fun progress(): MutableLiveData<Int> = progress

    fun toastMsg(): SingleLiveEvent<String> = toastMsg

    fun snackbarMsg(): SingleLiveEvent<String> = snackbarMsg

    fun onStartDownload(view: View) {

        MediaDownloader.Builder()
                .urls(repo.gallery())
                .okHttpFactory(DefaultOkHttpFactory.INSTANCE)
                .build()
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ progress.postValue(it) }, { toastMsg.postValue(it.message) }, { snackbarMsg.postValue("已经下载到${DefaultOkHttpFactory.dirPath}") })
    }
}