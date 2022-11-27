package dev.hnatiuk.navigation.simplesample.fragments

import android.graphics.Color
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.core.extensions.toast
import dev.hnatiuk.navigation.databinding.FragmentRootBinding
import dev.hnatiuk.navigation.lib.asRoute
import dev.hnatiuk.navigation.lib.navController
import dev.hnatiuk.navigation.simplesample.MainRoute

class RootFragment : BaseFragment<FragmentRootBinding>() {

    override val bindingFactory: Inflate<FragmentRootBinding>
        get() = FragmentRootBinding::inflate

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
    }

    private fun openBox(color: Int) {
        val route = MainRoute.BOX to BoxArguments(color)
        navController.navigate(route.asRoute)
    }
}