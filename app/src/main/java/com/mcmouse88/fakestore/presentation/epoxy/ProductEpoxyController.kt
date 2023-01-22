package com.mcmouse88.fakestore.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.presentation.epoxy.models.ProductEpoxyModel
import com.mcmouse88.fakestore.domain.models.Product

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(product = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}