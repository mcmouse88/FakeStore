package com.mcmouse88.fakestore.presentation

import androidx.lifecycle.ViewModel
import com.mcmouse88.fakestore.presentation.redux.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import com.mcmouse88.fakestore.presentation.redux.reducer.ProductListReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val productListReducer: ProductListReducer
) : ViewModel() {
}