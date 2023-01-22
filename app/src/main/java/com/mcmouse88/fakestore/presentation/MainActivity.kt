package com.mcmouse88.fakestore.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mcmouse88.fakestore.databinding.ActivityMainBinding
import com.mcmouse88.fakestore.presentation.epoxy.ProductEpoxyController
import com.mcmouse88.fakestore.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw NullPointerException("ActivityMainBinding is null")

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val controller = ProductEpoxyController()
        binding.rvProductList.setController(controller)
        controller.setData(emptyList())

        viewModel.store.stateFlow
            .map { it.products }
            .distinctUntilChanged()
            .observe(this) { products ->
            controller.setData(products)
        }

        viewModel.fetchProducts()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}