package com.mcmouse88.fakestore.presentation.epoxy.models

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ProductItemBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel
import com.mcmouse88.fakestore.domain.models.Product
import java.text.NumberFormat

data class ProductEpoxyModel(
    val product: Product?
) : ViewBindingKotlinModel<ProductItemBinding>(R.layout.product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun ProductItemBinding.bind() {
        shimmerLayout.root.isVisible = product == null
        cardView.isInvisible = product == null

        product?.let {
            shimmerLayout.root.stopShimmer()

            tvProductTitle.text = product.title
            tvProductDescription.text = product.description
            tvProductCategory.text = product.category
            tvProductPrice.text = currencyFormatter.format(product.price)

            progressProductImageLoading.isVisible = true
            ivProduct.load(
                data = product.image
            ) {
                listener { request, result ->
                    progressProductImageLoading.isGone = true
                }
            }
        } ?: shimmerLayout.root.startShimmer()

    }
}