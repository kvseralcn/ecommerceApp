package com.pixelark.capstoneproject.ui.favorite.presentation

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentFavoriteBinding
import com.pixelark.capstoneproject.ui.favorite.domain.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate, FavoriteViewModel::class.java
) {
    override fun onFragmentStarted() {
    }
}