package io.example.sample.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.widget.AppCompatSpinner
import android.widget.ArrayAdapter
import android.widget.TextView
import io.example.sample.R
import io.example.sample.utils.PasswordError


@BindingAdapter(value = ["entries"])
fun bindSpinnerData(spinner: AppCompatSpinner, data: List<String>) {
    val spinnerAdapter = ArrayAdapter<String>(spinner.context, android.R.layout.simple_spinner_item, data)
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = spinnerAdapter
    spinnerAdapter.notifyDataSetChanged()
}

@BindingAdapter(value = ["passwordError"])
fun bindPasswordError(textView: TextInputLayout, errorState: PasswordError?) {

    val errorText = when (errorState) {
        PasswordError.MINIMUM_LENGTH -> R.string.password_error_min_length
        PasswordError.NO_SPECIAL -> R.string.password_error_no_special
        PasswordError.UPPERCASE_MISSING -> R.string.password_error_upercase_missing
        PasswordError.DIGIT_MISSING -> R.string.password_error_digit_missing
        PasswordError.NONE, null -> null
    }

    textView.error = if (errorText == null) {
        null
    } else {
        textView.context.getString(errorText)
    }
}

@BindingAdapter(value = ["emailError"])
fun bindEmailError(textView: TextInputLayout, isError: Boolean?) {
    textView.error = if (isError == true) {
        textView.context.getString(R.string.email_error)
    } else {
        null
    }
}

@BindingAdapter(value = ["dialingCodeError"])
fun bindDialingCodeError(spinner: AppCompatSpinner, isError: Boolean?) {
    val textView = (spinner.selectedView ?: return) as TextView
    textView.error = if (isError == true) {
        textView.context.getString(R.string.dialing_code_error)
    } else {
        null
    }
}