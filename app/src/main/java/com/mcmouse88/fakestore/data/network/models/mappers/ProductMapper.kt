package com.mcmouse88.fakestore.data.network.models.mappers

import com.mcmouse88.fakestore.domain.models.Product
import com.mcmouse88.fakestore.models.nerwork.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    private fun buildFrom(networkProduct: NetworkProduct): Product {
        return Product(
            id = networkProduct.id,
            title = networkProduct.title,
            image = networkProduct.image,
            category = capitalize(networkProduct.category),
            description = networkProduct.description,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP)
        )
    }

    fun buildFromList(listNetworkProduct: List<NetworkProduct>?): List<Product> {
        return listNetworkProduct?.map(::buildFrom)
            ?: emptyList()
    }

    private fun capitalize(sequence: String): String {
        return sequence.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}