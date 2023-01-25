package com.mcmouse88.fakestore.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
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

fun<T> T.launchWithLifecycle(owner: LifecycleOwner, block: suspend (T) -> Unit) {
    owner.lifecycleScope.launch {
        owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block(this@launchWithLifecycle)
        }
    }
}

fun Context.getCompatColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

@Dimension(unit = Dimension.PX)
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

@Dimension(unit = Dimension.PX)
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
