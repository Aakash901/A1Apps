package com.example.a1apps.view

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
import com.example.a1apps.repository.model.MangaData

class MangaAdapter(
    private var mangaList: List<MangaData>,
    private val navController: NavController
) :
    RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangaList[position]

        holder.titleTextView.text = manga.title
        Glide.with(holder.itemView.context)
            .load(manga.thumb)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.authorImageView)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", manga.title)
                putString("thumb", manga.thumb)
                putString("summary", manga.summary)
                putString("author", manga.authors[0])
                putString("status", manga.status)
                putStringArrayList("genres", ArrayList(manga.genres))
            }
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    fun setData(newMangaList: List<MangaData>) {
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorImageView: ImageView = itemView.findViewById(R.id.imageVie)
    }
}
