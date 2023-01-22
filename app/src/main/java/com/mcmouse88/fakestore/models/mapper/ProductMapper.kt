package com.mcmouse88.fakestore.models.mapper

import com.mcmouse88.fakestore.models.domain.Product
import com.mcmouse88.fakestore.models.nerwork.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun buildFrom(networkProduct: NetworkProduct): Product {
        return Product(
            id = networkProduct.id,
            title = networkProduct.title,
            image = networkProduct.image,
            category = networkProduct.category,
            description = networkProduct.description,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP)
        )
    }

    fun buildFromList(listNetworkProduct: List<NetworkProduct>?): List<Product> {
        return listNetworkProduct?.map(::buildFrom)
            ?: emptyList()
    }
}