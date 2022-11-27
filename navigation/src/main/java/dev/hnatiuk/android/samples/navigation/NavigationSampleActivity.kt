package dev.hnatiuk.android.samples.navigation

import android.content.Intent
import android.view.LayoutInflater
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.navigation.complexsample.splash.SplashNavigationActivity
import dev.hnatiuk.android.samples.navigation.databinding.ActivityNavigationSampleBinding
import dev.hnatiuk.android.samples.navigation.simplesample.SimpleNavigationSampleActivity

class NavigationSampleActivity : BaseActivity<ActivityNavigationSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityNavigationSampleBinding
        get() = ActivityNavigationSampleBinding::inflate

    override fun ActivityNavigationSampleBinding.initUI() {
        simpleNavigationSample.setOnClickListener {
            startActivity(SimpleNavigationSampleActivity.getIntent(this@NavigationSampleActivity))
        }
        complexNavigationSample.setOnClickListener {
            startActivity(SplashNavigationActivity.getIntent(this@NavigationSampleActivity).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    companion object : SimpleIntentProvider(NavigationSampleActivity::class.java)
}