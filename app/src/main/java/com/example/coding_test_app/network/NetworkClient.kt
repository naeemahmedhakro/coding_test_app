package com.example.coding_test_app.network

import com.example.coding_test_app.utils.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    companion object{
        val apiService: InRequestData by lazy {
            Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Convert JSON to Kotlin objects
                .build()
                .create(InRequestData::class.java)
        }
    }
}