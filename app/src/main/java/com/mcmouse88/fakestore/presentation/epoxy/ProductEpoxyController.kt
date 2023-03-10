package com.mcmouse88.fakestore.presentation.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.domain.models.Filter
import com.mcmouse88.fakestore.presentation.epoxy.models.ProductEpoxyModel
import com.mcmouse88.fakestore.presentation.epoxy.models.ProductFilterEpoxyModel
import com.mcmouse88.fakestore.presentation.redux.state.ProductListState

class ProductEpoxyController(
    private val listener: Listener
) : TypedEpoxyController<ProductListState>() {

    override fun buildModels(data: ProductListState?) {
        when (data) {
            ProductListState.Loading -> {
                repeat(7) {
                    val epoxyId = it + 1
                    ProductEpoxyModel(
                        uiProduct = null,
                        listener = listener
                    ).id(epoxyId).addTo(this)
                }
            }
            is ProductListState.Success -> {
                val uiFilterModels = data.filters.map {
                    ProductFilterEpoxyModel(
                        filterUI = it,
                        onFilterSelected = listener::onFilterSelected
                    )
                        .id(it.filter.value)
                }
                CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

                data.products.forEach { product ->
                    ProductEpoxyModel(
                        uiProduct = product,
                        listener = listener
                    ).id(product.product.id).addTo(this)
                }
            }
            null -> throw RuntimeException("Unhandled branch! $data")
        }
    }

    interface Listener {
        fun onFavoriteIconClick(productId: Int)
        fun onProductClick(productId: Int)
        fun onFilterSelected(filter: Filter)
        fun addToCardClick(productId: Int)
    }
}