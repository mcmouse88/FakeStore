package com.mcmouse88.fakestore.epoxy.models

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ProductItemBinding
import com.mcmouse88.fakestore.epoxy.ViewBindingKotlinModel
import com.mcmouse88.fakestore.models.domain.Product

data class ProductEpoxyModel(
    val product: Product
) : ViewBindingKotlinModel<ProductItemBinding>(R.layout.product_item) {

    override fun ProductItemBinding.bind() {
        tvProductTitle.text = product.title
        tvProductDescription.text = product.description
        tvProductCategory.text = product.category

        progressProductImageLoading.isVisible = true
        ivProduct.load(
            data = product.image
        ) {
            listener { request, result ->
                progressProductImageLoading.isGone = true
            }
        }
    }
}