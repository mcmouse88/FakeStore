package com.mcmouse88.fakestore.presentation.epoxy.models

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ProductItemBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel
import com.mcmouse88.fakestore.presentation.models.ProductUI
import java.text.NumberFormat

data class ProductEpoxyModel(
    val uiProduct: ProductUI?
) : ViewBindingKotlinModel<ProductItemBinding>(R.layout.product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun ProductItemBinding.bind() {
        shimmerLayout.root.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let {
            shimmerLayout.root.stopShimmer()

            tvProductTitle.text = uiProduct.product.title
            tvProductDescription.text = uiProduct.product.description
            tvProductCategory.text = uiProduct.product.category
            tvProductPrice.text = currencyFormatter.format(uiProduct.product.price)

            btnFavorite.setIconResource(
                if (uiProduct.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            )

            progressProductImageLoading.isVisible = true
            ivProduct.load(
                data = uiProduct.product.image
            ) {
                listener { _, _ ->
                    progressProductImageLoading.isGone = true
                }
            }
        } ?: shimmerLayout.root.startShimmer()

    }
}