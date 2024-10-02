package dev.hnatiuk.android.samples.navigation.simplesample

import android.view.LayoutInflater
import androidx.fragment.app.commit
import androidx.navigation.NavController
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.navigation.R
import dev.hnatiuk.android.samples.navigation.databinding.ActivitySimpleNavigationSampleBinding
import dev.hnatiuk.android.samples.navigation.lib.BaseHostActivity

class SimpleNavigationSampleActivity : BaseHostActivity<ActivitySimpleNavigationSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivitySimpleNavigationSampleBinding
        get() = ActivitySimpleNavigationSampleBinding::inflate

    override fun NavController.getGraph() = navController.createSimpleGraph()

    override fun ActivitySimpleNavigationSampleBinding.initUI() {
        action.setOnClickListener {
            supportFragmentManager.commit {
                setCustomAnimations(
                    R.anim.crossfade_enter,
                    R.anim.corssfade_exit,
                    R.anim.crossfade_enter,
                    R.anim.corssfade_exit,
                )
                val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
                show(fragment!!)
            }
        }

        supportFragmentManager.commit {
            val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
            hide(fragment!!)
        }
    }

    companion object : SimpleIntentProvider(SimpleNavigationSampleActivity::class.java)
}