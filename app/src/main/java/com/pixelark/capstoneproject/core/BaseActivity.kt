package com.pixelark.capstoneproject.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int,
    private val inflate: Inflate<VB>,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProvider(this)[viewModelClass]
    }
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater, null, false)
        setContentView(_binding!!.root)
        onActivityStarted()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun onActivityStarted()
}