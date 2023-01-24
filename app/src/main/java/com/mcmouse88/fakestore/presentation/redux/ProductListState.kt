package com.mcmouse88.fakestore.presentation.redux

import com.mcmouse88.fakestore.presentation.models.FilterUI
import com.mcmouse88.fakestore.presentation.models.ProductUI

data class ProductListState(
    val products: List<ProductUI>,
    val filters: Set<FilterUI>
)
