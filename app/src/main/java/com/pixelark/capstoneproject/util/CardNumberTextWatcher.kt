package com.pixelark.capstoneproject.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CardNumberTextWatcher(private val editText: EditText) : TextWatcher {

    companion object {
        const val WHITE_SPACE = " "
        const val EMPTY_STRING = ""
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        val text = p0.toString().replace(WHITE_SPACE, EMPTY_STRING)

        val formattedText = StringBuilder()
        var count = 0

        for (i in text.indices) {
            formattedText.append(text[i])
            count++

            if (count % 4 == 0 && i < text.length - 1) {
                formattedText.append(WHITE_SPACE)
            }
        }

        editText.removeTextChangedListener(this)
        editText.setText(formattedText)
        editText.setSelection(formattedText.length)
        editText.addTextChangedListener(this)
    }
}