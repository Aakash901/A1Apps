package com.example.a1apps.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a1apps.R
import com.example.a1apps.repository.favouriteDB.FavoriteManga
import com.example.a1apps.viewmodel.FavouriteViewModel

class FavoriteMangaAdapter(
    private var favoriteMangaList: List<FavoriteManga>,
    private val navController: NavController,
    private val viewModel: FavouriteViewModel,
    private val context: Context
) : RecyclerView.Adapter<FavoriteMangaAdapter.FavoriteMangaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_manga, parent, false)
        return FavoriteMangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMangaViewHolder, position: Int) {
        val favoriteManga = favoriteMangaList[position]

        holder.titleTextView.text = favoriteManga.title
        Glide.with(holder.itemView.context)
            .load(favoriteManga.thumb)
            .centerCrop()
            .placeholder(R.drawable.manga)
            .into(holder.authorImageView)

        holder.iconImageView.setOnClickListener {
            viewModel.delete(
                FavoriteManga(
                    favoriteManga.id,
                    favoriteManga.title,
                    favoriteManga.thumb,
                    favoriteManga.summary,
                    favoriteManga.authors,
                    favoriteManga.status,
                    favoriteManga.genres
                ),context
            )
            // Return true to indicate that the long click event is consumed
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", favoriteManga.title)
                putString("thumb", favoriteManga.thumb)
                putString("summary", favoriteManga.summary)
                putString("author", favoriteManga.authors[0])
                putString("status", favoriteManga.status)
                putStringArrayList("genres", ArrayList(favoriteManga.genres))
            }
            navController.navigate(R.id.action_favouriteFragment_to_detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return favoriteMangaList.size
    }

    fun setData(newFavoriteMangaList: List<FavoriteManga>) {
        favoriteMangaList = newFavoriteMangaList
        notifyDataSetChanged()
    }

    inner class FavoriteMangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorImageView: ImageView = itemView.findViewById(R.id.imageVie)
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
    }
}
