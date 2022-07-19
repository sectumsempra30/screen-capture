package com.hnatiuk.navigation.complexsample.entry

import android.os.Parcelable
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.hnatiuk.core.extensions.toast
import com.hnatiuk.navigation.complexsample.graphs.EntryRoute
import com.hnatiuk.navigation.complexsample.graphs.getEntryGraph
import com.hnatiuk.navigation.databinding.ActivityNavigationEntryBinding
import com.hnatiuk.navigation.lib.BaseHostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntryNavigationArg(
    val isSignIn: Boolean
) : Parcelable

@AndroidEntryPoint
class EntryNavigationActivity : BaseHostActivity<ActivityNavigationEntryBinding>() {

    private val arg by lazy {
        intent.getParcelableExtra<EntryNavigationArg>("arg")
            ?: throw IllegalArgumentException("No argument for EntryNavigationActivity")
    }

    override val bindingFactory: (LayoutInflater) -> ActivityNavigationEntryBinding
        get() = ActivityNavigationEntryBinding::inflate

    override fun ActivityNavigationEntryBinding.initUI() {
        toast(arg.isSignIn.toString())
    }

    override fun NavController.getGraph(): NavGraph {
        val startDestination = if (arg.isSignIn) EntryRoute.HOME else EntryRoute.SIGN_IN
        return navController.getEntryGraph(startDestination)
    }
}