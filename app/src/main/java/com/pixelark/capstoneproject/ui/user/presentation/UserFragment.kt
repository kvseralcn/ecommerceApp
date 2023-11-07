package com.pixelark.capstoneproject.ui.user.presentation

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentUserBinding
import com.pixelark.capstoneproject.ui.user.domain.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>(
    FragmentUserBinding::inflate, UserViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentUserBtnSignOut.setOnClickListener() {
            viewModel.signOut()
        }
        binding.fragmentUserBtnUpdate.setOnClickListener {
            viewModel.updateUser(
                hashMapOf<String, Any>(
                    "phone" to binding.fragmentUserTvUserPhoneNumber.editText?.text.toString(),
                    "address" to binding.fragmentUserTvUserAddress.editText?.text.toString()
                )
            )
        }

        viewModel.readData()
        viewModel.userData.observe(viewLifecycleOwner) { userModel ->
            binding.fragmentUserTvUserName.editText?.setText(userModel.name)
            binding.fragmentUserTvUserAddress.editText?.setText(userModel.address)
            binding.fragmentUserTvUserPhoneNumber.editText?.setText(userModel.phone)
        }

        binding.fragmentUserTvUserPhoneNumber.editText?.addTextChangedListener(object :
            TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) {
                    return
                }
                isFormatting = true

                val phoneNumber = s.toString()
                val formattedPhoneNumber = StringBuilder()
                for (i in phoneNumber.indices) {
                    if (i == 3 || i == 7 || i == 10) {
                        formattedPhoneNumber.append(' ')
                        if (phoneNumber[i] != ' ') {
                            formattedPhoneNumber.append(phoneNumber[i])
                        }
                    } else {
                        formattedPhoneNumber.append(phoneNumber[i])
                    }
                }

                if (s.toString() != formattedPhoneNumber.toString()) {
                    binding.fragmentUserTvUserPhoneNumber.editText?.setText(formattedPhoneNumber.toString())
                    binding.fragmentUserTvUserPhoneNumber.editText?.setSelection(
                        formattedPhoneNumber.length
                    )
                }

                isFormatting = false
            }
        })

        binding.fragmentUserBtnSignOut.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}