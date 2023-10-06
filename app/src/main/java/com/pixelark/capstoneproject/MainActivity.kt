package com.pixelark.capstoneproject

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pixelark.capstoneproject.core.BaseActivity
import com.pixelark.capstoneproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {
    private lateinit var navController: NavController
    override fun onActivityStarted() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        val bottomNavView = binding.bottomNavView
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.searchFragment, R.id.cartFragment, R.id.favoriteFragment -> bottomNavView.isVisible =
                    true

                else -> bottomNavView.isVisible = false
            }
        }
    }
}