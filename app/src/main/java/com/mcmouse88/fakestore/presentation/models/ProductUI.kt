package com.mcmouse88.fakestore.presentation.models

import com.mcmouse88.fakestore.domain.models.Product

data class ProductUI(
    val product: Product,
    val isFavorite: Boolean = false,
    val isExpanded: Boolean = false
)