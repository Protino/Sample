package io.example.sample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.example.sample.api.SampleApi
import io.example.sample.api.model.LoginResponse
import io.example.sample.api.model.User
import io.example.sample.api.model.UserForm
import io.example.sample.common.Event
import io.example.sample.utils.PasswordError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

/**
 * Repository that interacts with models. Be it with local storage or network.
 *
 * In this case it takes care about making network call to login and expose the result.
 */
class LoginModel {

    private val loginResult: MutableLiveData<LoginResponse> = MutableLiveData()
    fun getLoginResult(): LiveData<LoginResponse> = loginResult

    fun loginUser(id: String, password: String) {

        val userForm = UserForm(id, password)
        // Not sending for now, since the api is dummy a endpoint
        SampleApi.getService().loginUser()
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // TODO: Expose failure response
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val body = response.body()
                        loginResult.value = body
                    }

                })
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

}