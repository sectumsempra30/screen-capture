package dev.hnatiuk.android.samples

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

const val SYMBOL_PLUS = "+"
const val UA_COUNTRY_CODE = "38"

const val UA_COUNTRY_CODE_PLUS_SYMBOL = "$SYMBOL_PLUS$UA_COUNTRY_CODE"

fun getClearPhoneNumberWithoutCountryCode(
    value: String, removeCountryCode: Boolean = true
): String {
    val clearValue = value.filter { it.isDigit() }
    return if (removeCountryCode) clearValue.removePrefix(UA_COUNTRY_CODE) else clearValue
}

class PhoneTextWatcher(private val editText: EditText) : TextWatcher {

    companion object {

        private const val FORMATTING_PATTERN = "(***) *** ** **"
        private const val FORMATTING_SYMBOL = '*'
    }

    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
        // No action needed before text changes
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
        // No action needed during text changes
    }

    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)

        val text = getClearPhoneNumberWithoutCountryCode(editable.toString())


        val formattedNumber = getFormattedPhoneNumber(text)
        editText.setText(formattedNumber)
        editText.setSelection(formattedNumber.length)
        editText.addTextChangedListener(this)
    }

    private fun getFormattedPhoneNumber(phoneNumber: String): String {
        val clearValue = getClearPhoneNumberWithoutCountryCode(phoneNumber)
        var formattedValue = FORMATTING_PATTERN

        clearValue.forEach {
            formattedValue = formattedValue.replaceFirst(FORMATTING_SYMBOL, it)
        }

        val lastDigitIndex = formattedValue.indexOfLast { it.isDigit() }
        val formattedNumberClearPattern = formattedValue.substring(startIndex = 0, endIndex = lastDigitIndex + 1)

        return "$UA_COUNTRY_CODE_PLUS_SYMBOL $formattedNumberClearPattern"
    }
}