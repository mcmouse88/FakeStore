package com.mcmouse88.fakestore.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.FragmentCartBinding
import com.mcmouse88.fakestore.presentation.CartViewModel
import com.mcmouse88.fakestore.presentation.epoxy.CartFragmentEpoxyController
import com.mcmouse88.fakestore.presentation.epoxy.models.CartItemEpoxyModel
import com.mcmouse88.fakestore.presentation.models.ProductUI
import com.mcmouse88.fakestore.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartItemEpoxyModel.Listener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = _binding ?: throw NullPointerException("FragmentCartBinding is null")

    private val viewModel by viewModels<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        val controller = CartFragmentEpoxyController(
            listener = this,
            onClick = {
                // todo handle click
            }
        )
        binding.rvCard.setController(controller)

        viewModel.productListReducer.reduce(
            store = viewModel.store
        )
            .map { uiProduct -> uiProduct.filter { it.isInCart } }
            .distinctUntilChanged().observe(viewLifecycleOwner) { products ->
                val viewState = if (products.isEmpty()) UIState.Empty
                else UIState.NonEmpty(products)
                controller.setData(viewState)
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onFavoriteClicked() {
        TODO("Not yet implemented")
    }

    override fun onDeleteClicked() {
        TODO("Not yet implemented")
    }

    sealed interface UIState {
        object Empty : UIState
        data class NonEmpty(val products: List<ProductUI>) : UIState
    }
}