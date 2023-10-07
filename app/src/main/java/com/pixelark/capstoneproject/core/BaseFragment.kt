package com.pixelark.capstoneproject.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>,

    private val viewModelClass: Class<VM>
) : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this)[viewModelClass]
    }
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    lateinit var viewModelProvider: ViewModelProvider
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentStarted()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    abstract fun onFragmentStarted()
}