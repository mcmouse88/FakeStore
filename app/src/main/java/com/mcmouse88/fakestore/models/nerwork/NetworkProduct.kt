package com.mcmouse88.fakestore.models.nerwork

data class NetworkProduct(
    val id: Int,
    val title: String,
    val category: String,
    val image: String,
    val description: String,
    val price: Double,
    val rating: Rating
)

data class Rating(
    val count: Int,
    val rate: Double
)