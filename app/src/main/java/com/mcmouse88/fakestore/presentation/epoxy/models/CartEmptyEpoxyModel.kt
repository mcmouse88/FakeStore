package com.mcmouse88.fakestore.presentation.epoxy.models

import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ItemCartEmptyBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel

class CartEmptyEpoxyModel(
    private val onClick: () -> Unit
) : ViewBindingKotlinModel<ItemCartEmptyBinding>(R.layout.item_cart_empty) {

    override fun ItemCartEmptyBinding.bind() {
        btnGoShopping.setOnClickListener { onClick.invoke() }
    }
}