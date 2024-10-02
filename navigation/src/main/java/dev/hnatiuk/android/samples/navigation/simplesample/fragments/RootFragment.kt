package dev.hnatiuk.android.samples.navigation.simplesample.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.core.extensions.toast
import dev.hnatiuk.android.samples.navigation.databinding.FragmentRootBinding
import dev.hnatiuk.android.samples.navigation.lib.asRoute
import dev.hnatiuk.android.samples.navigation.lib.navController
import dev.hnatiuk.android.samples.navigation.simplesample.MainRoute
import kotlinx.coroutines.launch

class RootFragment : BaseFragment<FragmentRootBinding>() {

    override val bindingFactory: Inflate<FragmentRootBinding>
        get() = FragmentRootBinding::inflate

    val viewModel by viewModels<RootViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            //lifecycle.whenCreated { toast("load view") }
        }
    }

    override fun FragmentRootBinding.initUI() {
        greenBox.setOnClickListener {
            openBox(Color.GREEN)
        }
        yellowBox.setOnClickListener {
            openBox(Color.YELLOW)
        }
        parentFragmentManager.setFragmentResultListener(BoxFragment.RANDOM_NUMBER_REQUEST_CODE, viewLifecycleOwner) { _, data ->
            val number = data.getInt(BoxFragment.RANDOM_NUMBER_KEY)
            toast("Generated number - $number")
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        toast("hidden changed > $hidden")
    }

    private fun openBox(color: Int) {
        val route = MainRoute.BOX to BoxArguments(color)
        navController.navigate(route.asRoute)
    }
}