package com.example.a1apps.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a1apps.R
import com.example.a1apps.databinding.FragmentFavouriteBinding
import com.example.a1apps.view.adapter.FavoriteMangaAdapter
import com.example.a1apps.viewmodel.FavouriteViewModel

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var favoriteMangaAdapter: FavoriteMangaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        // Observe LiveData and update TextView
        viewModel.allFavoriteManga.observe(viewLifecycleOwner) { favoriteMangaList ->
            val text = buildString {
                append("Favourite Manga:\n")
                favoriteMangaList.forEachIndexed { index, favoriteManga ->
                    append("${index + 1}. ${favoriteManga.title}\n")
                }
            }
        }

        favoriteMangaAdapter = FavoriteMangaAdapter(emptyList(), findNavController(), viewModel,requireContext())
        binding.recView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = favoriteMangaAdapter
        }

        // Observe LiveData and update RecyclerView
        viewModel.allFavoriteManga.observe(viewLifecycleOwner) { favoriteMangaList ->
            if (favoriteMangaList.isEmpty()) {
                binding.swipeRefreshLayout.visibility = View.GONE
                binding.noItemLayout.visibility = View.VISIBLE
            } else {
                binding.swipeRefreshLayout.visibility = View.VISIBLE
                binding.noItemLayout.visibility = View.GONE
                favoriteMangaAdapter.setData(favoriteMangaList)
            }
        }

        binding.toolBar.setOnClickListener {
            findNavController().navigate(R.id.action_favouriteFragment_to_homeFragment2)
        }

        return binding.root
    }
}
