package dev.hnatiuk.navigation

import android.content.Intent
import android.view.LayoutInflater
import dev.hnatiuk.core.base.BaseActivity
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.navigation.complexsample.splash.SplashNavigationActivity
import dev.hnatiuk.navigation.databinding.ActivityNavigationSampleBinding
import dev.hnatiuk.navigation.simplesample.SimpleNavigationSampleActivity

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