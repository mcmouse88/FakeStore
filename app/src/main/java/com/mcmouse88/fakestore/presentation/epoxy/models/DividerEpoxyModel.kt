package com.mcmouse88.fakestore.presentation.epoxy.models

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ItemDividerBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel

data class DividerEpoxyModel(
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int = 0,
    @Dimension(unit = Dimension.PX) private val verticalMargin: Int = 0,
) : ViewBindingKotlinModel<ItemDividerBinding>(R.layout.item_divider) {

    override fun ItemDividerBinding.bind() {
        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        }
    }
}
