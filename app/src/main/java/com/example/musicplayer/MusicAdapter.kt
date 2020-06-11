package com.example.musicplayer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class MusicAdapter(private val musicDataset: ArrayList<Music>): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>()
{
    class MusicViewHolder(val view: View): RecyclerView.ViewHolder(view)
    {
        var title: TextView
        var artist: TextView
        var music: ConstraintLayout
        init
        {
            this.title = view.findViewById(R.id.title) as TextView
            this.artist = view.findViewById(R.id.artist) as TextView
            this.music = view.findViewById(R.id.music) as ConstraintLayout
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder
    {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.music_list, parent, false)
        val viewHolder: MusicViewHolder = MusicViewHolder(inflate)
        return viewHolder
    }

    override fun getItemCount(): Int
    {
        return musicDataset.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int)
    {
        holder.title.text = musicDataset[position].title
        holder.artist.text = musicDataset[position].artist
        holder.music.setOnClickListener{ musicDataset[position].clickListener()}
    }

}