package info.hellovass.gallerydownloader.sample.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import com.trello.rxlifecycle2.LifecycleProvider

abstract class BaseVM(val provider: LifecycleProvider<Lifecycle.Event>) : ViewModel()