package com.example.a1apps.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a1apps.R
import com.example.a1apps.databinding.FragmentFavouriteBinding
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        binding.toolBar.setOnClickListener {
            findNavController().navigate(R.id.action_favouriteFragment_to_homeFragment2)
        }
        return binding.root
    }


}
