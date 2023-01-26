package com.mcmouse88.fakestore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.fakestore.domain.ProductRepository
import com.mcmouse88.fakestore.presentation.redux.state.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import com.mcmouse88.fakestore.presentation.redux.reducer.ProductListReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val productListReducer: ProductListReducer,
    private val productRepository: ProductRepository,
    private val generator: FilterGenerator
) : ViewModel() {

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        val products = productRepository.fetchAllProduct()
        store.update { state ->
            return@update state.copy(
                products = products,
                filter = ApplicationState.ProductFilterInfo(
                    filters = generator.generateFrom(products),
                    selectedFilter = null
                )
            )
        }
    }
}