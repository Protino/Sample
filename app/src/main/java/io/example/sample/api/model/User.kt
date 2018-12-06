package io.example.sample.api.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("userId") val id: String,
        @SerializedName("name") val name: String)