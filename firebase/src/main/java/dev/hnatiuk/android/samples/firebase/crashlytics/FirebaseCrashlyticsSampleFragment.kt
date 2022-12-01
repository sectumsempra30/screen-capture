package dev.hnatiuk.android.samples.firebase.crashlytics;

import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.firebase.FirebaseSampleActivity
import dev.hnatiuk.android.samples.firebase.databinding.FragmentFirebaseCrashlyticsSampleBinding
import java.util.*

class FirebaseCrashlyticsSampleFragment : BaseFragment<FragmentFirebaseCrashlyticsSampleBinding>() {

    override val bindingFactory: Inflate<FragmentFirebaseCrashlyticsSampleBinding>
        get() = FragmentFirebaseCrashlyticsSampleBinding::inflate

    override fun FragmentFirebaseCrashlyticsSampleBinding.initUI() {
        throwButton.setOnClickListener {
            val message = binding.errorMessage
                .text
                .toString()
                .ifEmpty { "Some error at ${Date()}" }

            throw FirebaseSampleActivity.ExceptionForFirebase(message)
        }
    }

    companion object {

        fun newInstance() = FirebaseCrashlyticsSampleFragment()
    }
}