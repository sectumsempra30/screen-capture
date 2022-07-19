package com.hnatiuk.navigation.simplesample.fragments

import android.graphics.Color
import com.hnatiuk.core.base.BaseFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.core.extensions.toast
import com.hnatiuk.navigation.databinding.FragmentRootBinding
import com.hnatiuk.navigation.lib.asRoute
import com.hnatiuk.navigation.lib.navController
import com.hnatiuk.navigation.simplesample.MainRoute

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