package io.example.sample.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SampleApi {
    private val client = OkHttpClient.Builder()
    private val service: ApiService

    init {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://www.api.sample.com")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(client.build())

        service = retrofitBuilder.build().create(ApiService::class.java)
    }

    fun getService(): ApiService {
        return service
    }
}
