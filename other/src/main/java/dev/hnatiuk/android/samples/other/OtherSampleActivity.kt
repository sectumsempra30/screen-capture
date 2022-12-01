package dev.hnatiuk.android.samples.other

import android.annotation.SuppressLint
import android.view.LayoutInflater
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.other.databinding.ActivityOtherSampleBinding
import dev.hnatiuk.android.samples.other.lib.EditTextLostFocusOutsideFragment

class OtherSampleActivity : BaseActivity<ActivityOtherSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityOtherSampleBinding
        get() = ActivityOtherSampleBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun ActivityOtherSampleBinding.initUI() {
        val instance = EditTextLostFocusOutsideFragment.newInstance()

        fragmentTitle.text = ">> ${instance::class.java.simpleName}"
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, instance)
            .commit()

        fragmentTitle.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .remove(instance)
                .commit()
        }
    }

    companion object : SimpleIntentProvider(OtherSampleActivity::class.java)
}