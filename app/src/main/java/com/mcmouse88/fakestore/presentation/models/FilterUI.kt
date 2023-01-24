package com.mcmouse88.fakestore.presentation.models

import com.mcmouse88.fakestore.domain.models.Filter

data class FilterUI(
    val filter: Filter,
    val isSelected: Boolean
)