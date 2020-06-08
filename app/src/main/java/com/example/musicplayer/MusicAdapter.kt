package com.example.musicplayer

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val musicDataset: Array<String>): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>()
{
    class MusicViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder
    {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int
    {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int)
    {
        TODO("Not yet implemented")
    }

}