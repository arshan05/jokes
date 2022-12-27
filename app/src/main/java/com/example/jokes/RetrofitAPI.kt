package com.example.jokes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitAPI {
    @GET("/")
    @Headers("Accept: application/json")
    fun getJoke() : Call<Joke>
}