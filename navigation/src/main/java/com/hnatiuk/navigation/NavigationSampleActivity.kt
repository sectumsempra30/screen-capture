package com.hnatiuk.navigation

import android.content.Intent
import android.view.LayoutInflater
import com.hnatiuk.core.base.BaseActivity
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.navigation.complexsample.splash.SplashNavigationActivity
import com.hnatiuk.navigation.databinding.ActivityNavigationSampleBinding
import com.hnatiuk.navigation.simplesample.SimpleNavigationSampleActivity

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