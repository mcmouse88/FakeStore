package com.mcmouse88.fakestore.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.FragmentProductListBinding
import com.mcmouse88.fakestore.domain.models.Filter
import com.mcmouse88.fakestore.presentation.ProductListViewModel
import com.mcmouse88.fakestore.presentation.epoxy.ProductEpoxyController
import com.mcmouse88.fakestore.presentation.models.FilterUI
import com.mcmouse88.fakestore.presentation.models.ProductUI
import com.mcmouse88.fakestore.presentation.redux.ProductListState
import com.mcmouse88.fakestore.utils.launchWithLifecycle
import com.mcmouse88.fakestore.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list),
    ProductEpoxyController.Listener {

    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding
        get() = _binding ?: throw NullPointerException("FragmentProductListBinding is null")

    private val viewModel by viewModels<ProductListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val controller = ProductEpoxyController(
            listener = this
        )
        binding.rvProductList.setController(controller)

        setupObserver(controller)
        // controller.setData(emptyList()) todo
        viewModel.fetchProducts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onFavoriteIconClick(productId: Int) {
        viewModel.store.launchWithLifecycle(viewLifecycleOwner) { store ->
            store.update { currentState ->
                val currentFavoriteIds = currentState.favoriteProductIds
                val newFavoriteIds = if (currentFavoriteIds.contains(productId)) {
                    currentFavoriteIds.filter { it != productId }.toSet()
                } else {
                    currentFavoriteIds + setOf(productId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }

    override fun onProductClick(productId: Int) {
        viewModel.store.launchWithLifecycle(viewLifecycleOwner) { store ->
            store.update { currentState ->
                val currentExpandedIds = currentState.expandedProductIds
                val newExpandedIds = if (currentExpandedIds.contains(productId)) {
                    currentExpandedIds.filter { it != productId }.toSet()
                } else {
                    currentExpandedIds + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpandedIds)
            }
        }
    }

    override fun onFilterSelected(filter: Filter) {
        viewModel.store.launchWithLifecycle(viewLifecycleOwner) { store ->
            store.update { currentState ->
                val currencySelectedFilter = currentState.filter.selectedFilter
                return@update currentState.copy(
                    filter = currentState.filter.copy(
                        selectedFilter = if (currencySelectedFilter != filter) filter
                        else null
                    )
                )
            }
        }
    }

    private fun setupObserver(controller: ProductEpoxyController) {
        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds },
            viewModel.store.stateFlow.map { it.expandedProductIds },
            viewModel.store.stateFlow.map { it.filter }
        ) { listProducts, setFavorites, setExpanded, filterInfo ->
            val uiProduct = listProducts.map { product ->
                ProductUI(
                    product = product,
                    isFavorite = setFavorites.contains(product.id),
                    isExpanded = setExpanded.contains(product.id)
                )
            }

            val uiFilter = filterInfo.filters.map { filter ->
                FilterUI(
                    filter = filter,
                    isSelected = filterInfo.selectedFilter?.equals(filter) == true
                )
            }.toSet()

            val filteredProduct = if (filterInfo.selectedFilter == null) {
                uiProduct
            } else {
                uiProduct.filter { it.product.category == filterInfo.selectedFilter.value }
            }
            return@combine ProductListState(filteredProduct, uiFilter)
        }.distinctUntilChanged().observe(viewLifecycleOwner) { products ->
            controller.setData(products)
        }
    }
}