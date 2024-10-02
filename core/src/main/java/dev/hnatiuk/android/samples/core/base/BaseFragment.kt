package dev.hnatiuk.android.samples.core.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var binding: VB
    protected abstract val bindingFactory: Inflate<VB>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("checkFragment", "${this::class.java.simpleName} >> onCreateView | ${this::binding.isInitialized}")
        binding = bindingFactory.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("checkFragment", "${this::class.java.simpleName} >> onViewCreated")
        binding.initUI()
    }

    open fun VB.initUI() {
        //no op
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("checkFragment", "${this::class.java.simpleName} >> onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("checkFragment", "${this::class.java.simpleName} >> onCreate")
    }
}