package io.example.sample.ui

import android.arch.lifecycle.*
import android.content.Context
import io.example.sample.utils.*

/**
 * context here is the applicationContext.
 */
class LoginViewModel(context: Context) : ViewModel() {

    val emailPhoneText: MutableLiveData<String> = MutableLiveData()
    val passwordText: MutableLiveData<String> = MutableLiveData()

    val dialingCodes: ArrayList<String> = CountryToDialogCodePrefix.getAllCodes()
    val selectedDialogCodePosition: MutableLiveData<Int> = MutableLiveData()

    val isPhoneEntered: LiveData<Boolean> = Transformations.map(emailPhoneText) {
        return@map isPhoneNumber(it)
    }


    // Error states
    val emailError: MutableLiveData<Boolean> = MutableLiveData()
    val dialingCodeError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: MutableLiveData<PasswordError> = MutableLiveData()

    init {

        // init default country code
        val selectedIndex = dialingCodes.indexOf(getDialingCode(context))
        if (selectedIndex != -1) {
            selectedDialogCodePosition.value = selectedIndex
        }

    }

    fun onSubmit() {
        var isError = false
        if (isPhoneEntered.value != true) {
            //Validate email
            emailError.value = !isValidEmail(emailPhoneText.value)
            isError = true
        }

        if (selectedDialogCodePosition.value == null) {
            // No dialog code selected
            dialingCodeError.value = true
            isError = true
        }

        passwordError.value = isValidPassword(passwordText.value)

        if (!isError && passwordError.value == PasswordError.NONE) {
            //make api call
        }
    }

}

class LoginViewModelFactory(var context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}