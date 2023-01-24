package com.mcmouse88.fakestore.presentation

import com.mcmouse88.fakestore.domain.models.Filter
import com.mcmouse88.fakestore.domain.models.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    // todo test me
    fun generateFrom(productList: List<Product>): Set<Filter> {
        return productList
            .groupBy { it.category }
            .map { Filter(value = it.key, displayText = "${it.key} (${it.value.size})") }
            .toSet()
    }
}