package com.pixelark.capstoneproject.ui.user.presentation

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentUserBinding
import com.pixelark.capstoneproject.ui.user.domain.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>(
    FragmentUserBinding::inflate, UserViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentPaymentBtnSignOut.setOnClickListener() {
            viewModel.signOut()
        }
        viewModel.readData()
        viewModel.userData.observe(viewLifecycleOwner) { userModel ->
            binding.fragmentUserTvUserName.editText?.setText(userModel.name)
            binding.fragmentUserTvUserBirthdate.editText?.setText(userModel.birthdate)
            //binding.fragmentUserTvUserBirthdate.editText?.setText(userModel.email)
            binding.fragmentUserTvUserAddress.editText?.setText(userModel.address)
            binding.fragmentUserTvUserPhoneNumber.editText?.setText(userModel.phone)
        }
    }
}