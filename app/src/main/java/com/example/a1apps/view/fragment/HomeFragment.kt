package com.example.a1apps.view.fragment

import android.os.Build
import android.os.Bundle
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
import com.example.a1apps.repository.offlineDB.OfflineManga
import com.example.a1apps.view.adapter.MangaAdapter
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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.init(requireContext())

        mangaAdapter = MangaAdapter(emptyList(), navController,viewModel,requireContext())

        setStatusBarColor()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = binding.progressBar
        binding.recView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mangaAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData(requireContext())
        }

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

    override fun onDestroy() {
        super.onDestroy()

        viewModel.mangaList.value?.let { mangaList ->
            viewModel.insertAllOfflineManga(mangaList.map { mangaData ->
                OfflineManga(
                    id = mangaData.id,
                    title = mangaData.title,
                    thumb = mangaData.thumb,
                    summary = mangaData.summary,
                    authors = mangaData.authors,
                    status = mangaData.status,
                    genres = mangaData.genres
                )
            })
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.mangaList.value?.let { mangaList ->
            viewModel.insertAllOfflineManga(mangaList.map { mangaData ->
                OfflineManga(
                    id = mangaData.id,
                    title = mangaData.title,
                    thumb = mangaData.thumb,
                    summary = mangaData.summary,
                    authors = mangaData.authors,
                    status = mangaData.status,
                    genres = mangaData.genres
                )
            })
        }

    }


}
