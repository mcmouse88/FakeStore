package com.mcmouse88.fakestore.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.observe(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                block(it)
            }
        }
    }
}