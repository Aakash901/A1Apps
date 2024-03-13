package com.example.a1apps.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.a1apps.R
import com.example.a1apps.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setStatusBarColor()
        binding = FragmentDetailBinding.inflate(layoutInflater)
        val title = arguments?.getString("title")
        val thumb = arguments?.getString("thumb")
        val summary = arguments?.getString("summary")
        val status = arguments?.getString("status")
        val author = arguments?.getString("author")
        val genres = arguments?.getStringArrayList("genres")
        binding.summaryTV.text = summary

        val stringBuilder = StringBuilder()


        genres?.forEachIndexed { index, genre ->
            stringBuilder.append("#$genre")
            if (index != genres.size - 1) {
                stringBuilder.append("\n")
            }
        }

        binding.summaryTV.append("\n")
        binding.summaryTV.append(stringBuilder.toString())

        binding.movieName.text = title
        if (author != "") {
            binding.authorName.text = author

        } else {
            binding.authorName.text = "NA"
        }
        binding.statusTv.text = status
        Glide.with(requireContext())
            .load(thumb)
            .centerCrop()
            .placeholder(R.drawable.manga)
            .into(binding.movieLogo)



        return binding.root
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.white2)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}