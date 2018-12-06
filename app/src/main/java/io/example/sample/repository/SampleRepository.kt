package io.example.sample.repository

import android.arch.lifecycle.MutableLiveData
import io.example.sample.api.SampleApi
import io.example.sample.api.model.LoginResponse
import io.example.sample.api.model.User
import io.example.sample.api.model.UserForm
import io.example.sample.common.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository that interacts with models. Be it with local storage or network.
 *
 * In this case it takes care about making network call to login and expose the result.
 *
 */
object SampleRepository {

    fun loginUser(id: String, password: String): MutableLiveData<Event<RepositoryResponse<User>>> {
        val apiResponse: MutableLiveData<Event<RepositoryResponse<User>>> = MutableLiveData()

        val userForm = UserForm(id, password)
        SampleApi.getService().loginUser(userForm)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        apiResponse.value = Event(RepositoryResponse(null, true))
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val body = response.body()
                        apiResponse.value = Event(RepositoryResponse(body?.data, body != null && body.isSuccess))
                    }

                })

        return apiResponse
    }

}