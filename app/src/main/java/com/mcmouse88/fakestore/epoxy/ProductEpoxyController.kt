package com.mcmouse88.fakestore.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.epoxy.models.ProductEpoxyModel
import com.mcmouse88.fakestore.models.domain.Product

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data == null || data.isEmpty()) {
            // todo loading state
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}