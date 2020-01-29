package io.example.sample.utils

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import java.util.*

/**
 * Gets the dialogcode using the country code of the device
 * *based on locale
 */
fun getDialingCode(context: Context): String? {
    return CountryToDialogCodePrefix.prefixFor(getDeviceCountryCode(context))
}


/**
 * Get ISO 3166-1 alpha-2 country code for this device (or empty string if not available).
 *
 */
private fun getDeviceCountryCode(context: Context): String {
    try {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simCountry = tm.simCountryIso
        if (simCountry != null && simCountry.length == 2) { // SIM country code is available
            return simCountry.toUpperCase(Locale.US)
        } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
            val networkCountry = tm.networkCountryIso
            if (networkCountry != null && networkCountry.length == 2) { // network country code is available
                return networkCountry.toUpperCase(Locale.US)
            }
        }
    } catch (ignored: Exception) {
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales.get(0).country
    } else {
        context.resources.configuration.locale.country
    }
}

enum class PasswordError {
    NONE,
    MINIMUM_LENGTH,
    NO_SPECIAL,
    UPPERCASE_MISSING,
    DIGIT_MISSING
}




