package com.mcmouse88.fakestore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.fakestore.domain.ProductRepository
import com.mcmouse88.fakestore.presentation.redux.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productRepository: ProductRepository
) : ViewModel() {

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        val products = productRepository.fetchAllProduct()
        store.update { state ->
            return@update state.copy(products = products)
        }
    }
}