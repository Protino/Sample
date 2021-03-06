package io.example.sample.api.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("success") val isSuccess: Boolean,
        @SerializedName("data") val data: User,
        @SerializedName("messages") val errorMessage: String)
