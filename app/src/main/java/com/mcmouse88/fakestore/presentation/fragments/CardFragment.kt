package com.mcmouse88.fakestore.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mcmouse88.fakestore.R
import com.mcmouse88.fakestore.databinding.FragmentCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card) {

    private var _binding: FragmentCardBinding? = null
    private val binding: FragmentCardBinding
        get() = _binding ?: throw NullPointerException("FragmentCardBinding is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCardBinding.bind(view)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}