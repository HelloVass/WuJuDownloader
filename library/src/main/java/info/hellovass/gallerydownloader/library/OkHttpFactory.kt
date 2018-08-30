package info.hellovass.gallerydownloader.library

import io.reactivex.ObservableSource

interface OkHttpFactory {

    fun download(url: String): ObservableSource<String>
}