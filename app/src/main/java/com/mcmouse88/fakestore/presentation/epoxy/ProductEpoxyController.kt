package com.mcmouse88.fakestore.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.presentation.epoxy.models.ProductEpoxyModel
import com.mcmouse88.fakestore.presentation.models.ProductUI

class ProductEpoxyController : TypedEpoxyController<List<ProductUI>>() {

    override fun buildModels(data: List<ProductUI>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(uiProduct = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.product.id).addTo(this)
        }
    }
}