package com.mcmouse88.fakestore.domain

import com.mcmouse88.fakestore.domain.models.Product

interface ProductRepository {

    suspend fun fetchAllProduct(): List<Product>
}
