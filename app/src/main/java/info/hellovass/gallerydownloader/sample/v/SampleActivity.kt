package info.hellovass.gallerydownloader.sample.v

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import info.hellovass.gallerydownloader.library.MediaScanner
import info.hellovass.gallerydownloader.sample.R
import info.hellovass.gallerydownloader.sample.databinding.ActivitySampleBinding
import info.hellovass.gallerydownloader.sample.ext.obtainVM
import info.hellovass.gallerydownloader.sample.vm.SampleVM

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = obtainVM(SampleVM::class.java)

        val binding = DataBindingUtil.setContentView<ActivitySampleBinding>(this,
                R.layout.activity_sample)

        binding.apply {
            vm = viewModel
            setLifecycleOwner(this@SampleActivity)
        }

        viewModel.toastMsg().observe(this, Observer { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        })

        viewModel.snackbarMsg().observe(this, Observer { savePath ->

            savePath?.let { it ->
                Snackbar.make(binding.root, "已经下载到 $it 目录", Snackbar.LENGTH_LONG).show()
                MediaScanner.scanDir(this, it)
            }
        })
    }
}
