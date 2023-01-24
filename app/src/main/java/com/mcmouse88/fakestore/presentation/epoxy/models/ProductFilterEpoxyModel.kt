package com.mcmouse88.fakestore.presentation.epoxy.models

import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ItemProductFilterBinding
import com.mcmouse88.fakestore.domain.models.Filter
import com.mcmouse88.fakestore.presentation.epoxy.ViewBindingKotlinModel
import com.mcmouse88.fakestore.presentation.models.FilterUI
import com.mcmouse88.fakestore.utils.getCompatColor

data class ProductFilterEpoxyModel(
    val filterUI: FilterUI,
    val onFilterSelected: (Filter) -> Unit
) : ViewBindingKotlinModel<ItemProductFilterBinding>(R.layout.item_product_filter) {

    override fun ItemProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(filterUI.filter) }
        tvFilter.text = filterUI.filter.displayText
        tvFilter.isSelected = filterUI.isSelected
        root.setCardBackgroundColor(
            root.context.getCompatColor(
                if (filterUI.isSelected) R.color.purple_500
                else R.color.purple_200
            )
        )
    }
}