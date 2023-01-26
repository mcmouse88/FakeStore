package com.mcmouse88.fakestore.presentation.redux.state

import com.mcmouse88.fakestore.presentation.models.FilterUI
import com.mcmouse88.fakestore.presentation.models.ProductUI
import javax.inject.Inject

class ProductsListFragmentUiStateGenerator @Inject constructor() {

    fun generate(
        productUIs: List<ProductUI>,
        productFilterInfo: ApplicationState.ProductFilterInfo
    ): ProductListState {
        if (productUIs.isEmpty()) {
            return ProductListState.Loading
        }

        val filterUI = productFilterInfo.filters.map { filter ->
            FilterUI(
                filter = filter,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
            )
        }.toSet()

        val filteredProduct = if (productFilterInfo.selectedFilter == null) productUIs
        else productUIs.filter { it.product.category == productFilterInfo.selectedFilter.value }

        return ProductListState.Success(filterUI, filteredProduct)
    }
}