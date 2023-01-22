package com.mcmouse88.fakestore.data.network.services

import com.mcmouse88.fakestore.models.nerwork.NetworkProduct
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getAllProduct(): Response<List<NetworkProduct>>
}