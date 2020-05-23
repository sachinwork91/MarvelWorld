package com.example.marvelworld

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelEndPoint {
    @GET("characters")
    fun findCharacters(@Query("apikey") apiKey: String, @Query("ts") ts: String,
        @Query("hash") hash: String, @Query("offset") offset: String, @Query("limit") limit: String): Call<MyResponse>
}
