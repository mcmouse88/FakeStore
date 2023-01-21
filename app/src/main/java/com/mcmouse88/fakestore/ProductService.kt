package com.mcmouse88.fakestore

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getAllProduct(): Response<List<Any>>
}