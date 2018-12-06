package io.example.sample.ui

import android.arch.lifecycle.*
import android.content.Context
import io.example.sample.utils.*
import io.example.sample.repository.SampleRepository

/**
 * context here is the applicationContext.
 */
class LoginViewModel(context: Context) : ViewModel() {

    val emailPhoneText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()

    val dialingCodes: ArrayList<String> = CountryToDialogCodePrefix.getAllCodes()
    val selectedDialogCodePosition = MutableLiveData<Int>()

    val isPhoneEntered: LiveData<Boolean> = Transformations.map(emailPhoneText) {
        return@map isPhoneNumber(it)
    }

    // Error states
    val emailError = MutableLiveData<Boolean>()
    val dialingCodeError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<PasswordError>()

    init {

        // init default country code
        val selectedIndex = dialingCodes.indexOf(getDialingCode(context))
        if (selectedIndex != -1) {
            selectedDialogCodePosition.value = selectedIndex
        }

    }

    fun onSubmit() {
        var id = emailPhoneText.value
        val password = passwordText.value

        if (isValidForm(id, password)) {
            if (isPhoneEntered.value == true) {
                id = dialingCodes[selectedDialogCodePosition.value!!] + id
            }
            SampleRepository.loginUser(id!!, password!!)
        }
    }

    private fun isValidForm(id: String?, password: String?): Boolean {
        var isError = false

        if (selectedDialogCodePosition.value == null) {
            // No dialing code selected
            dialingCodeError.value = true
            isError = true
        }

        if (isPhoneEntered.value != true) {
            //Validate email
            emailError.value = !isValidEmail(id)
            isError = true
        } else {
            emailError.value = false
        }

        passwordError.value = isValidPassword(password)
        return !isError && passwordError.value == PasswordError.NONE
    }

}

class LoginViewModelFactory(private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}