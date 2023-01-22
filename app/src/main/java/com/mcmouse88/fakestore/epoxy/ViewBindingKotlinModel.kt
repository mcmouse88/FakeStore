@file:Suppress("UNCHECKED_CAST")

package com.mcmouse88.fakestore.epoxy

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyModel
import com.mcmouse88.fakestore.R
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

abstract class ViewBindingKotlinModel<T : ViewBinding>(
    @LayoutRes private val layoutRes: Int
) : EpoxyModel<View>() {

    private val bindingMethod by lazy { getBindMethodFrom(this::class.java) }

    abstract fun T.bind()

    override fun bind(view: View) {
        var binding = view.getTag(R.id.epoxy_view_binding) as? T
        if (binding == null) {
            binding = bindingMethod.invoke(null, view) as T
            view.setTag(R.id.epoxy_view_binding, binding)
        }
        binding.bind()
    }

    override fun getDefaultLayout(): Int = layoutRes
}

private val sBindingMethodByClass = ConcurrentHashMap<Class<*>, Method>()

@Synchronized
private fun getBindMethodFrom(javaClass: Class<*>): Method {
    return sBindingMethodByClass.getOrPut(javaClass) {
        val actualTypeOfThis = getSuperClassParametrizedType(javaClass)
        val viewBindingClass = actualTypeOfThis.actualTypeArguments[0] as Class<ViewBinding>
        viewBindingClass.getDeclaredMethod("bind", View::class.java)
            ?: error("The binder class ${javaClass.canonicalName} should have a method bind(view)")
    }
}

private fun getSuperClassParametrizedType(klass: Class<*>): ParameterizedType {
    val genericSuperClass = klass.genericSuperclass
    return (genericSuperClass as? ParameterizedType)
        ?: getSuperClassParametrizedType(genericSuperClass as Class<*>)
}