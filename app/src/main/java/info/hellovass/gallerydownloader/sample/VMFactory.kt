package info.hellovass.gallerydownloader.sample

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.trello.rxlifecycle2.LifecycleProvider
import info.hellovass.gallerydownloader.sample.vm.SampleVM

@Suppress("UNCHECKED_CAST")
class VMFactory(private val provider: LifecycleProvider<Lifecycle.Event>) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        private var INSTANCE: VMFactory? = null

        fun getInstance(provider: LifecycleProvider<Lifecycle.Event>) = INSTANCE
                ?: synchronized(VMFactory::class.java) {
                    INSTANCE ?: VMFactory(provider).also {
                        INSTANCE = it
                    }
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SampleVM::class.java) -> {
                SampleVM(provider)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    } as T
}