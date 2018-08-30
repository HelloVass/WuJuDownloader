package info.hellovass.gallerydownloader.sample.ext

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.LifecycleProvider
import info.hellovass.gallerydownloader.sample.VMFactory

fun <T : ViewModel> AppCompatActivity.obtainVM(viewModelClass: Class<T>): T {

    val provider: LifecycleProvider<Lifecycle.Event>
            = AndroidLifecycle.createLifecycleProvider(this)

    return ViewModelProviders.of(this,
            VMFactory.getInstance(provider)).get(viewModelClass)
}
