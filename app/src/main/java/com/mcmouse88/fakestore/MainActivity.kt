package com.mcmouse88.fakestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mcmouse88.fakestore.databinding.ActivityMainBinding
import com.mcmouse88.fakestore.epoxy.ProductEpoxyController
import com.mcmouse88.fakestore.models.mapper.ProductMapper
import com.mcmouse88.fakestore.network.ProductService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw NullPointerException("ActivityMainBinding is null")

    @Inject
    lateinit var productService: ProductService

    @Inject
    lateinit var productMapper: ProductMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val controller = ProductEpoxyController()
        binding.rvProductList.setController(controller)

        lifecycleScope.launchWhenStarted {
            val response = productService.getAllProduct()
            val domainProduct = productMapper.buildFromList(response.body())
            controller.setData(domainProduct)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}