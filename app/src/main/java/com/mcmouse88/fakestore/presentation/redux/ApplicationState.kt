package com.mcmouse88.fakestore.presentation.redux

import com.mcmouse88.fakestore.domain.models.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet()
)