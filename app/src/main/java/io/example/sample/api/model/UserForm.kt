package io.example.sample.api.model

import com.google.gson.annotations.SerializedName

data class UserForm(
        @SerializedName("user_id") val userId: String,
        @SerializedName("password") val password: String)