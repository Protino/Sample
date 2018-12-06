package io.example.sample.utils

import org.junit.Test
import org.junit.Assert.*

class UtilsKtTest {

    @Test
    fun testIsPhoneNumber() {
        val testCases = mapOf(
                "1234567890" to true,
                "098765432" to false,
                "12345678901" to false,
                "@#$76890980" to false
        )

        testCases.forEach { t, u ->
            assertEquals("Invalid for $t", u, isPhoneNumber(t))
        }
    }

    @Test
    fun testIsValidPassword() {
        val testCases = mapOf(
                "9872364" to PasswordError.MINIMUM_LENGTH,
                "aj23A" to PasswordError.MINIMUM_LENGTH,
                "" to PasswordError.MINIMUM_LENGTH,
                "A&^*SDTYUG" to PasswordError.NO_SPECIAL,
                "ASUKHGD**" to PasswordError.NO_SPECIAL,
                "\\adaA23jskdn" to PasswordError.NO_SPECIAL,
                "aaaaaa987s" to PasswordError.UPPERCASE_MISSING,
                "1234567A" to PasswordError.NONE,
                "AAAAAAA124" to PasswordError.NONE,
                "8276135AHSDasd" to PasswordError.NONE,
                "aIIsdguybj" to PasswordError.DIGIT_MISSING)

        testCases.forEach { t, u ->
            assertEquals("Invalid for $t", u, isValidPassword(t))
        }
    }
}