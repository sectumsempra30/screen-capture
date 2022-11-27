package dev.hnatiuk.android.samples.navigation.simplesample.fragments

import android.os.Parcelable
import androidx.core.os.bundleOf
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.navigation.databinding.FragmentBoxBinding
import dev.hnatiuk.android.samples.navigation.lib.asRoute
import dev.hnatiuk.android.samples.navigation.lib.navController
import dev.hnatiuk.android.samples.navigation.simplesample.MainRoute
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class BoxArguments(
    val color: Int
) : Parcelable

class BoxFragment : BaseFragment<FragmentBoxBinding>() {

    override val bindingFactory: Inflate<FragmentBoxBinding> = FragmentBoxBinding::inflate

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

