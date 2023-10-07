package com.pixelark.capstoneproject.ui.search.presentation

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentSearchBinding
import com.pixelark.capstoneproject.ui.search.domain.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate, SearchViewModel::class.java
) {
    override fun onFragmentStarted() {
    }
}