package com.hnatiuk.features.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hnatiuk.core.SimpleIntentProvider
import com.hnatiuk.features.R
import com.hnatiuk.features.databinding.ActivityNavigationSampleBinding

class NavigationSampleActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityNavigationSampleBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_sample)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.graph = navController.createMainGraph()
    }

    companion object : SimpleIntentProvider(NavigationSampleActivity::class.java)
}