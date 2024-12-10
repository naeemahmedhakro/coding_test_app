package com.example.coding_test_app.network

import com.example.coding_test_app.contact.NewUser
import com.example.coding_test_app.contact.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InRequestData {
    @GET("users")
    fun getUsers(): Call<List<User>>  // For fetching users

    @POST("users")
    fun addUser(@Body newUser: NewUser): Call<User>  // For adding a new user

}