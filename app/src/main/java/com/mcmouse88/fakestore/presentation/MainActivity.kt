package com.mcmouse88.fakestore.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.ActivityMainBinding
import com.mcmouse88.fakestore.presentation.redux.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import com.mcmouse88.fakestore.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw NullPointerException("ActivityMainBinding is null")

    @Inject
    lateinit var store: Store<ApplicationState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.productListFragment,
                R.id.cardFragment,
                R.id.profileFragment
            )
        )
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        // To prevent snapping in carousels
        Carousel.setDefaultGlobalSnapHelperFactory(null)

        store.stateFlow
            .map { it.inCartProductIds.size }
            .distinctUntilChanged()
            .observe(this) { cartSize ->
                binding.bottomNavView.getOrCreateBadge(R.id.cardFragment).apply {
                    isVisible = cartSize > 0
                    number = cartSize
                }

            }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}