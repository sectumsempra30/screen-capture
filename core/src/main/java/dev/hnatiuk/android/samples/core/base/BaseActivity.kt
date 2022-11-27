package dev.hnatiuk.android.samples.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract val bindingFactory: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory.invoke(layoutInflater)
        setContentView(binding.root)
        binding.initUI()
    }

    open fun VB.initUI() {
        //no op
    }
}