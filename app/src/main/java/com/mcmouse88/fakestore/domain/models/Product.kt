package com.mcmouse88.fakestore.domain.models

import java.math.BigDecimal

data class Product(
    val id: Int,
    val title: String,
    val image: String,
    val category: String,
    val description: String,
    val price: BigDecimal
)