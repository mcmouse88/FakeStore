package com.mcmouse88.fakestore.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mcmouse88.fakestore.databinding.ActivityMainBinding
import com.mcmouse88.fakestore.presentation.epoxy.ProductEpoxyController
import com.mcmouse88.fakestore.presentation.models.ProductUI
import com.mcmouse88.fakestore.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw NullPointerException("ActivityMainBinding is null")

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val controller = ProductEpoxyController(
            onFavoriteIconClick = ::onFavoriteClick
        )
        binding.rvProductList.setController(controller)

        setupObserver(controller)
        controller.setData(emptyList())
        viewModel.fetchProducts()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupObserver(controller: ProductEpoxyController) {
        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds },
        ) { listProducts, setFavorites ->
            listProducts.map { product ->
                ProductUI(
                    product = product,
                    isFavorite = setFavorites.contains(product.id)
                )
            }
        }.distinctUntilChanged().observe(this) { products ->
            controller.setData(products)
        }
    }

    private fun onFavoriteClick(selectedProductId: Int) {
        lifecycleScope.launch {
            viewModel.store.update { currentState ->
                val currentFavoriteIds = currentState.favoriteProductIds
                val newFavoriteIds = if (currentFavoriteIds.contains(selectedProductId)) {
                    currentFavoriteIds.filter { it != selectedProductId }.toSet()
                } else {
                    currentFavoriteIds + setOf(selectedProductId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }
}