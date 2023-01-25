package com.mcmouse88.fakestore.presentation.epoxy.models

import android.view.ViewGroup
import androidx.annotation.Dimension
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ItemVerticalSpaceBinding
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel

class VerticalSpaceEpoxyModel(
    @Dimension(unit = Dimension.PX) val height: Int
) : ViewBindingKotlinModel<ItemVerticalSpaceBinding>(R.layout.item_vertical_space) {

    override fun ItemVerticalSpaceBinding.bind() {
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
    }
}