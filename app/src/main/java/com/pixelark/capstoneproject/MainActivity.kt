package com.pixelark.capstoneproject

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
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
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.cartFragment,
                R.id.favoriteFragment -> binding.bottomNavView.isVisible =
                    true

                else -> binding.bottomNavView.isVisible = false
            }
        }
    }

    fun callCardService() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let { viewModel.getCartCount(it) }
        viewModel.cartCountData.observe(this) { response ->
            updateCartBadgeCount(response.count)
        }
    }

    fun updateCartBadgeCount(count: Int) {
        val menuItemId = R.id.cart_graph
        val badge = binding.bottomNavView.getOrCreateBadge(menuItemId)
        badge.isVisible = count != 0
        badge.number = count
    }
}