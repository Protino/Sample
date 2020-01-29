package io.example.sample.ui

import android.content.Context
import androidx.lifecycle.*
import io.example.sample.repository.LoginModel
import io.example.sample.utils.CountryToDialogCodePrefix
import io.example.sample.utils.PasswordError
import io.example.sample.utils.getDialingCode

/**
 * context here is the applicationContext.
 */
class LoginViewModel(context: Context, private val model: LoginModel) : ViewModel() {

    val emailPhoneText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()

    val dialingCodes: ArrayList<String> = CountryToDialogCodePrefix.getAllCodes()
    val selectedDialogCodePosition = MutableLiveData<Int>()

    val isPhoneEntered: LiveData<Boolean> = Transformations.map(emailPhoneText) {
        return@map model.isPhoneNumber(it)
    }

    // Error states
    val emailError = MutableLiveData<Boolean>()
    val dialingCodeError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<PasswordError>()

    // Username field after login is successful
    val userName: LiveData<String> = Transformations.map(model.getLoginResult()) {
        it.data.name  //Transformations
    }

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
            model.loginUser(id!!, password!!)
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
            isError = !model.isValidEmail(id)
            emailError.value = isError
        } else {
            emailError.value = false
        }

        passwordError.value = model.isValidPassword(password)
        return !isError && passwordError.value == PasswordError.NONE
    }

}

class LoginViewModelFactory(private val context: Context, private val model: LoginModel)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context, model) as T
    }
}