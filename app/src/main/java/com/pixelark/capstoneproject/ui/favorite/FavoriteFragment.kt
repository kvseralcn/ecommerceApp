package com.pixelark.capstoneproject.ui.favorite

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate, FavoriteViewModel::class.java
) {
    override fun onFragmentStarted() {
    }
}