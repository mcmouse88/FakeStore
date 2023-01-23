package com.mcmouse88.fakestore.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.presentation.epoxy.models.ProductEpoxyModel
import com.mcmouse88.fakestore.presentation.models.ProductUI

class ProductEpoxyController(
    private val onFavoriteIconClick: (Int) -> Unit
) : TypedEpoxyController<List<ProductUI>>() {

    override fun buildModels(data: List<ProductUI>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(
                    uiProduct = null,
                    onFavoriteIconClick = onFavoriteIconClick
                ).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(
                uiProduct = product,
                onFavoriteIconClick = onFavoriteIconClick
            ).id(product.product.id).addTo(this)
        }
    }
}