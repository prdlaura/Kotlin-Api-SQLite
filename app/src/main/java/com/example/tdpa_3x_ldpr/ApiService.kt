package com.example.tdpa_3x_ldpr

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/breeds/image/random")
    suspend fun getImage(): Response<Imagen>
}