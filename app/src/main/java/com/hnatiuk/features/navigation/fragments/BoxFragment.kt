package com.hnatiuk.features.navigation.fragments

import android.os.Parcelable
import androidx.core.os.bundleOf
import com.hnatiuk.entry.base.BaseFragment
import com.hnatiuk.features.databinding.FragmentBoxBinding
import com.hnatiuk.features.navigation.MainRoute
import com.hnatiuk.features.navigation.asRoute
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class BoxArguments(
    val color: Int
) : Parcelable

class BoxFragment : BaseFragment<FragmentBoxBinding>(FragmentBoxBinding::inflate) {

    private val state by lazy {
        requireArguments().getParcelable<BoxArguments>("arg")!!
    }

    override fun FragmentBoxBinding.initUI() {
        goBack.setOnClickListener {
            navController.popBackStack()
        }
        openSecret.setOnClickListener {
            navController.navigate(MainRoute.SECRET.asRoute)
        }
        generateNumberAndBack.setOnClickListener {
            val number = Random.nextInt(100)
            parentFragmentManager.setFragmentResult(
                RANDOM_NUMBER_REQUEST_CODE,
                bundleOf(RANDOM_NUMBER_KEY to number)
            )
            navController.popBackStack()
        }

        root.setBackgroundColor(state.color)
    }

    companion object {

        const val RANDOM_NUMBER_REQUEST_CODE = "RANDOM_NUMBER_REQUEST_CODE"
        const val RANDOM_NUMBER_KEY = "RANDOM_NUMBER_KEY"
    }
}

