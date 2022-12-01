package dev.hnatiuk.android.samples.firebase

import android.view.LayoutInflater
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.firebase.databinding.ActivityFirebaseSampleBinding
import dev.hnatiuk.android.samples.firebase.remoteconfig.FirebaseRemoteConfigSampleFragment

class FirebaseSampleActivity : BaseActivity<ActivityFirebaseSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityFirebaseSampleBinding
        get() = ActivityFirebaseSampleBinding::inflate

    override fun ActivityFirebaseSampleBinding.initUI() {
        val instance = FirebaseRemoteConfigSampleFragment.newInstance()

        fragmentTitle.text = instance::class.java.simpleName
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, instance)
            .commit()
    }

    internal class ExceptionForFirebase(message: String) : RuntimeException(message)

    companion object : SimpleIntentProvider(FirebaseSampleActivity::class.java)
}