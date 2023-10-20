package com.pixelark.capstoneproject.ui.payment.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.BottomSheetValidThruBinding
import com.pixelark.capstoneproject.databinding.CustomAlertDialogBinding
import com.pixelark.capstoneproject.databinding.FragmentPaymentBinding
import com.pixelark.capstoneproject.ui.payment.domain.PaymentViewModel
import com.pixelark.capstoneproject.util.CardNumberTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>(
    FragmentPaymentBinding::inflate, PaymentViewModel::class.java
) {

    private var selectedMonth: String? = null
    private var selectedYear: String? = null

    override fun onFragmentStarted() {
        binding.fragmentPaymentEtCardNumber.addTextChangedListener(CardNumberTextWatcher(binding.fragmentPaymentEtCardNumber))
        binding.fragmentPaymentSpValidThruMonth.setOnClickListener {
            showBottomDialog(R.array.month_list) { selectedMonth ->
                this.selectedMonth = selectedMonth
                binding.fragmentPaymentTvMonth.text = selectedMonth
            }
        }
        binding.fragmentPaymentSpValidThruYear.setOnClickListener {
            showBottomDialog(R.array.year_list) { selectedYear ->
                this.selectedYear = selectedYear
                binding.fragmentPaymentTvYear.text = selectedYear
            }
        }
        binding.fragmentPaymentBtnPayment.setOnClickListener {
            if (isPageValid()) {
                findNavController().navigate(
                    PaymentFragmentDirections
                        .actionPaymentFragmentToPaymentCompleteFragment()
                )
            } else {
                val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)
                val myDialog = Dialog(requireContext())
                myDialog.setContentView(dialogBinding.root)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val window = myDialog.window
                val layoutParams = window?.attributes
                layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.attributes = layoutParams
                myDialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                myDialog.window!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bottom_sheet_background_tint
                    )
                )
                myDialog.window!!.setGravity(Gravity.CENTER)
                myDialog.show()

                dialogBinding.customAlertDialogBtnOk.setOnClickListener {
                    myDialog.dismiss()
                }

            }
        }
    }

    private fun isPageValid(): Boolean {
        var isValid = true
        if (selectedMonth == null) {
            isValid = false
        }
        if (selectedYear == null) {
            isValid = false
        }
        if (binding.fragmentPaymentEtName.text?.isEmpty() == true) {
            isValid = false
        }
        if (binding.fragmentPaymentEtCardNumber.text?.isEmpty() == true) {
            isValid = false
        }
        if (binding.fragmentPaymentEtCVV.text?.isEmpty() == true) {
            isValid = false
        }
        if (binding.fragmentPaymentEtAddress.text?.isEmpty() == true) {
            isValid = false
        }
        return isValid
    }

    private fun showBottomDialog(
        arrayResource: Int,
        onValueSelected: (String) -> Unit
    ) {
        val bottomSheetBinding = BottomSheetValidThruBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.bottom_sheet_background_tint
            )
        )
        dialog.window!!.setGravity(Gravity.BOTTOM)
        val validThruArray = resources.getStringArray(arrayResource)
        val validStringArray = validThruArray.map { it.toString() }.toTypedArray()
        bottomSheetBinding.bottomSheetValidThruNpSelect.maxValue = validThruArray.size - 1
        bottomSheetBinding.bottomSheetValidThruNpSelect.minValue = 0
        bottomSheetBinding.bottomSheetValidThruNpSelect.displayedValues = validStringArray

        bottomSheetBinding.fragmentPaymentTvSelect.setOnClickListener {
            val selectedIndex = bottomSheetBinding.bottomSheetValidThruNpSelect.value
            onValueSelected(validThruArray[selectedIndex])
            dialog.dismiss()
        }
    }
}