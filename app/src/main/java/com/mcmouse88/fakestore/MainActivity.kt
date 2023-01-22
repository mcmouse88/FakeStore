package com.mcmouse88.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.mcmouse88.fakestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw NullPointerException("ActivityMainBinding is null")

    @Inject
    lateinit var productService: ProductService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        refreshData()
        setupListeners()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun refreshData() {
        lifecycleScope.launchWhenStarted {
            binding.progressProductImageLoading.isVisible = true
            val response = productService.getAllProduct()
            binding.ivProduct.load(
                data = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
            ) {
                listener { _, _ ->
                    binding.progressProductImageLoading.isGone = true
                }
            }
            Log.e("TAG_RESP", "refreshData: $response")
        }
    }

    private fun setupListeners() {
        binding.cardView.setOnClickListener {
            binding.tvProductDescription.apply {
                isVisible = isVisible.not()
            }
        }

        binding.btnAddToCard.setOnClickListener {
            binding.btnIndicateInCard.apply {
                isVisible = isVisible.not()
            }
        }

        var isFavorite = false
        binding.btnFavorite.setOnClickListener {
            binding.btnFavorite.setIconResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            )
            isFavorite = isFavorite.not()
        }
    }
}