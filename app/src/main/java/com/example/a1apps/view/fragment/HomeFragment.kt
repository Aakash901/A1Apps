package com.example.a1apps.view.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a1apps.R
import com.example.a1apps.databinding.FragmentHomeBinding
import com.example.a1apps.view.MangaAdapter
import com.example.a1apps.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mangaAdapter: MangaAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val navController = findNavController()
        mangaAdapter = MangaAdapter(emptyList(), navController)

        setStatusBarColor()
        Log.d("HomeFragment", "onCreateCall ")

        binding.recView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mangaAdapter
        }

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData(requireContext())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = binding.progressBar

        viewModel.mangaList.observe(viewLifecycleOwner, Observer { mangaList ->
            mangaAdapter.setData(mangaList)
            binding.swipeRefreshLayout.isRefreshing = false
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.loadData(requireContext())
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.back)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }


}
