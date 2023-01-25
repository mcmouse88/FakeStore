package com.mcmouse88.fakestore.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.mcmouse88.fakestore.presentation.epoxy.models.CartEmptyEpoxyModel
import com.mcmouse88.fakestore.presentation.epoxy.models.CartItemEpoxyModel
import com.mcmouse88.fakestore.presentation.epoxy.models.DividerEpoxyModel
import com.mcmouse88.fakestore.presentation.epoxy.models.VerticalSpaceEpoxyModel
import com.mcmouse88.fakestore.presentation.fragments.CartFragment
import com.mcmouse88.fakestore.utils.toPx

class CartFragmentEpoxyController(
    private val listener: CartItemEpoxyModel.Listener,
    private val onClick: () -> Unit
) : TypedEpoxyController<CartFragment.UIState>() {

    override fun buildModels(data: CartFragment.UIState?) {
        when (data) {
            is CartFragment.UIState.NonEmpty -> {
                data.products.forEachIndexed { index, productUI ->

                    addVerticalStyling(index)

                    CartItemEpoxyModel(
                        productUI = productUI,
                        listener = listener,
                        horizontalMargin = 16.toPx()
                    ).id(productUI.product.id).addTo(this)
                }
            }

            is CartFragment.UIState.Empty, null -> {
                CartEmptyEpoxyModel(onClick = {
                    onClick.invoke()
                }).id("empty_state").addTo(this)
            }
        }
    }

    private fun addVerticalStyling(index: Int) {
        VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(
                horizontalMargin = 16.toPx()
            ).id("divider_$index").addTo(this)
        }

        VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)
    }
}