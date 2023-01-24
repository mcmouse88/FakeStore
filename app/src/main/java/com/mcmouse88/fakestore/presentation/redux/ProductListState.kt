package com.mcmouse88.fakestore.presentation.redux

import com.mcmouse88.fakestore.presentation.models.FilterUI
import com.mcmouse88.fakestore.presentation.models.ProductUI

sealed interface ProductListState {

    data class Success(
        val filters: Set<FilterUI>,
        val products: List<ProductUI>
    ) : ProductListState

    object Loading : ProductListState
}
