package dev.hnatiuk.android.samples.core.base

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseVMFragment<VB : ViewBinding, VM : ViewModel> : BaseFragment<VB>() {

    protected abstract val viewModel: VM

    protected open fun VM.observeViewModel() {
        //no op
    }
}