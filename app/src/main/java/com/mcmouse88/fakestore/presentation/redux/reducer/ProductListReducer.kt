package com.mcmouse88.fakestore.presentation.redux.reducer

import com.mcmouse88.fakestore.presentation.models.ProductUI
import com.mcmouse88.fakestore.presentation.redux.state.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductListReducer @Inject constructor() {

    fun reduce(store: Store<ApplicationState>): Flow<List<ProductUI>> {
        return combine(
            store.stateFlow.map { it.products },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.expandedProductIds },
            store.stateFlow.map { it.inCartProductIds }
        ) { listProducts, setFavorites, setExpanded, inCartInfo ->
            if (listProducts.isEmpty()) {
                return@combine emptyList<ProductUI>()
            }

            return@combine listProducts.map { product ->
                ProductUI(
                    product = product,
                    isFavorite = setFavorites.contains(product.id),
                    isExpanded = setExpanded.contains(product.id),
                    isInCart = inCartInfo.contains(product.id)
                )
            }
        }.distinctUntilChanged()
    }
}