package com.mcmouse88.fakestore.presentation.redux

import com.mcmouse88.fakestore.domain.models.Filter
import com.mcmouse88.fakestore.domain.models.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val filter: ProductFilterInfo = ProductFilterInfo(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet()
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}