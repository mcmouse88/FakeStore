package com.mcmouse88.fakestore.data

import com.mcmouse88.fakestore.domain.ProductRepository
import com.mcmouse88.fakestore.domain.models.Product
import com.mcmouse88.fakestore.data.network.models.mappers.ProductMapper
import com.mcmouse88.fakestore.data.network.services.ProductService
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) : ProductRepository {

    override suspend fun fetchAllProduct(): List<Product> {
        // todo better error handler
        val response = productService.getAllProduct()
        return productMapper.buildFromList(response.body())
    }
}