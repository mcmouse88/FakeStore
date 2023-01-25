package com.mcmouse88.fakestore.presentation.epoxy.models

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ItemCartProductBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel
import com.mcmouse88.fakestore.presentation.models.ProductUI

class CartItemEpoxyModel(
    private val productUI: ProductUI,
    private val listener: Listener,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int
) : ViewBindingKotlinModel<ItemCartProductBinding>(R.layout.item_cart_product) {

    override fun ItemCartProductBinding.bind() {
        tvTitleCart.text = productUI.product.title

        ivFavoriteCart.setIconResource(
            if (productUI.isFavorite) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border
        )

        ivFavoriteCart.setOnClickListener { listener.onFavoriteClicked() }
        ivDelete.setOnClickListener { listener.onDeleteClicked() }

        ivProductCart.load(productUI.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }
    }

    interface Listener {
        fun onFavoriteClicked()
        fun onDeleteClicked()
    }
}