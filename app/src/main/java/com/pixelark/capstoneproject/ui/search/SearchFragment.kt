package com.pixelark.capstoneproject.ui.search

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate, SearchViewModel::class.java
) {
    override fun onFragmentStarted() {
    }
}