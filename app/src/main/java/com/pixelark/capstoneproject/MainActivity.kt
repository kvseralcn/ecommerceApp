package com.pixelark.capstoneproject

import com.pixelark.capstoneproject.core.BaseActivity
import com.pixelark.capstoneproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {
    override fun onActivityStarted() {
    }
}