package com.pixelark.capstoneproject.validation

import java.util.regex.Pattern

class EmailInputValidation {

    companion object {
        private val VALID_EMAIL_ADDRESS_REGEX: Pattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

        fun validate(
            value: String?, validationData: EmailInputValidationData
        ): ValidationResult {
            return if (value.isNullOrEmpty()) {
                ValidationResult(
                    isSuccess = false, errorMessage = validationData.errorMessage
                )
            } else {
                ValidationResult(
                    isSuccess = VALID_EMAIL_ADDRESS_REGEX.matcher(value).find(),
                    errorMessage = validationData.errorMessage
                )

            }
        }
    }
}