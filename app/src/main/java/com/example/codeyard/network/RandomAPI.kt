package com.example.codeyard.network

import com.example.codeyard.RemoteResults
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomAPI {

    @GET("?")
    fun getRandomUser(@Query("results") results: Int): Single<RemoteResults>
}