package com.hnatiuk.features.navigation.fragments

import android.graphics.Color
import com.hnatiuk.core.toast
import com.hnatiuk.entry.base.BaseFragment
import com.hnatiuk.features.databinding.FragmentRootBinding
import com.hnatiuk.features.navigation.MainRoute
import com.hnatiuk.features.navigation.asRoute

class RootFragment : BaseFragment<FragmentRootBinding>(FragmentRootBinding::inflate) {

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