package dev.hnatiuk.android.samples.firebase

import android.view.LayoutInflater
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.firebase.databinding.ActivityFirebaseSampleBinding
import java.util.Date

class FirebaseSampleActivity : BaseActivity<ActivityFirebaseSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityFirebaseSampleBinding
        get() = ActivityFirebaseSampleBinding::inflate

    override fun ActivityFirebaseSampleBinding.initUI() {
        throwButton.setOnClickListener {
            val message = binding.errorMessage
                .text
                .toString()
                .ifEmpty { "Some error at ${Date()}" }

            throw ExceptionForFirebase(message)
        }
    }

    internal class ExceptionForFirebase(message: String) : RuntimeException(message)

    companion object : SimpleIntentProvider(FirebaseSampleActivity::class.java)
}