package io.example.sample.utils

import android.content.Context
import android.os.Build

/**
 * Gets the dialogcode using the country code of the device
 * *based on locale
 */
fun getDialingCode(context: Context): String? {
    return CountryToDialogCodePrefix.prefixFor(getCountryUsingLocale(context))
}


/**
 * Get ISO 3166-1 alpha-2 country code for this device (or empty string if not available).
 *
 * Does not use telephony manager, since that is not the requirement.
 */
private fun getCountryUsingLocale(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales.get(0).country
    } else {
        context.resources.configuration.locale.country
    }
}

fun isPhoneNumber(text: String?): Boolean {
    return text?.matches("^[0-9]{10}\$".toRegex()) ?: false
}

fun isValidEmail(email: String?): Boolean {
    return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Does not use long regex - ^(?=.*\d)(?!.+[!@#$%^&*`=(){}|:\\"?<>,./;'_+~-])(?=.*[A-Z]).{8,}$
 * Since that is hard to read, maintain and debug
 *
 */
fun isValidPassword(password: String?): PasswordError {
    if (password == null || password.length < 8) return PasswordError.MINIMUM_LENGTH
    if (!password.matches("^[a-zA-Z0-9]*$".toRegex())) return PasswordError.NO_SPECIAL
    if (!password.matches(".*[A-Z].*".toRegex())) return PasswordError.UPPERCASE_MISSING
    if (!password.matches(".*[0-9].*".toRegex())) return PasswordError.DIGIT_MISSING

    return PasswordError.NONE
}

enum class PasswordError {
    NONE,
    MINIMUM_LENGTH,
    NO_SPECIAL,
    UPPERCASE_MISSING,
    DIGIT_MISSING
}




