package io.example.sample.api

import io.example.sample.api.model.LoginResponse
import io.example.sample.api.model.UserForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    /**
     * Using a dummy api to call
     * Response is set to what was defined in the assignment
     */
    @GET("bins/15mary")
    fun loginUser(): Call<LoginResponse>
}